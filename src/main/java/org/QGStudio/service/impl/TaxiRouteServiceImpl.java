package org.QGStudio.service.impl;

import ch.hsr.geohash.GeoHash;
import lombok.extern.log4j.Log4j2;
import org.QGStudio.dao.RouteDao;
import org.QGStudio.exception.CheckException;
import org.QGStudio.model.AreaLocation;
import org.QGStudio.model.Location;
import org.QGStudio.model.TaxiLocation;
import org.QGStudio.runnable.RouteRunnable;
import org.QGStudio.service.TaxiRouteService;
import org.QGStudio.util.GCJ02_WGS84;
import org.QGStudio.util.GeoHashUtil;
import org.QGStudio.util.TableUtil;
import org.QGStudio.util.VerifyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Description : 出租车道路可视化的实现类
 * @Param :
 * @Return :
 * @Author : SheldonPeng
 * @Date : 2019-08-10
 */

@Service
@Log4j2
public class TaxiRouteServiceImpl implements TaxiRouteService {


    @Autowired
    private RouteDao routeDao;

    @Autowired
    private ThreadPoolTaskExecutor executor;

    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public List findTaxi(String time , int area) throws ParseException {

        if ( VerifyUtil.isEmpty(time)){
            throw new CheckException("请输入正确的时间!");
        }
        Location maxLocation = AreaLocation.MAX_MAP.get(area);
        Location minLocation = AreaLocation.MIN_MAP.get(area);
        // 获取开始时间和结束时间
        Date startTime = null;
        Date endTime = null;

        log.info("{}{}",maxLocation,minLocation);
        startTime = format.parse(time);
        endTime = format.parse(time);

        endTime.setMinutes(endTime.getMinutes() + 1);
        // 获得表名
        String table = TableUtil.getGpsdataTable(startTime);

        List list = routeDao.findTaxi(table,startTime,endTime,maxLocation.getLongitude(),
                minLocation.getLongitude(),maxLocation.getLatitude(),minLocation.getLatitude());

        log.info("查询的返回结果数量为：{}", list.size());
        return list;
    }

    @Override
    public List<TaxiLocation> findRoute(TaxiLocation taxiLocation) throws ParseException {
        if (VerifyUtil.isNull(taxiLocation)) {
            throw new CheckException("请输入正确的数据!");
        }
        if (VerifyUtil.isEmpty(taxiLocation.getLicenseplateno())) {
            throw new CheckException("请输入正确的车牌号!");
        }
        if (VerifyUtil.isEmpty(taxiLocation.getTime())) {
            throw new CheckException("请输入正确的时间!");
        }
        // 开始时间，结束时间
        Date startTime = format.parse(taxiLocation.getTime());
        Date endTime = format.parse(taxiLocation.getTime());

        // 利用countDownLatch对主线程进行阻塞
        CountDownLatch countDownLatch = new CountDownLatch(6);
        // 获得表名
        String table = TableUtil.getGpsdataTable(startTime);

        //结果集
        Map<Integer, List<TaxiLocation>> map = new HashMap<>();

        // 建立结果集
        for (int i = 0; i < 6; i++) {

            map.put(i, new ArrayList<>());
        }

        // 建立6条线程 以10分钟为粒度对一小时的车辆进行检索
        for (int i = 5; i >= 0; i--) {

            Date start = new Date();
            Date end = new Date();
            start.setTime(startTime.getTime());
            end.setTime(endTime.getTime());


            start.setMinutes(start.getMinutes() - 10 * (i + 1));
            end.setMinutes(end.getMinutes() - 10 * i);

            log.info("开始时间为:{}", startTime);
            executor.execute(new RouteRunnable(countDownLatch, map.get(i), start, end, table, taxiLocation.getLicenseplateno()));

        }

        try {
            if (!countDownLatch.await(60, TimeUnit.SECONDS)) {

                for (int i = 5; i >= 0; i--) {

                    // 清空数据,交由GC回收内存
                    map.get(i).clear();
                    map.remove(i);

                }
                throw new CheckException("查询超时,请稍后重试");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new CheckException("查询超时,请稍后重试");
        }

        // 筛选数据
        long time1 = System.currentTimeMillis();
        log.info("开始处理:{}", time1);

        List<TaxiLocation> routs = new ArrayList<>();
        for (int i = 5; i >= 0; i--) {

            routs.addAll(map.get(i));
            // 清空数据,交由GC回收内存
            map.get(i).clear();
            map.remove(i);
        }
        for (TaxiLocation taxi :
                routs) {
            GCJ02_WGS84.wgs84_To_Gcj02(taxi);
        }

        long time2 = System.currentTimeMillis();
        log.info("处理结束：{}", time2);
        return routs;

    }

    @Override
    public List<TaxiLocation> findLiveRoute(TaxiLocation taxiLocation) throws ParseException {

        if (VerifyUtil.isNull(taxiLocation)) {
            throw new CheckException("请输入正确的数据!");
        }
        if (VerifyUtil.isEmpty(taxiLocation.getLicenseplateno())) {
            throw new CheckException("请输入正确的车牌号!");
        }
        if (VerifyUtil.isEmpty(taxiLocation.getTime())) {
            throw new CheckException("请输入正确的时间!");
        }

        Date start = format.parse(taxiLocation.getTime());
        Date end = format.parse(taxiLocation.getTime());
        end.setSeconds(end.getSeconds() + 30 );
        String table = TableUtil.getGpsdataTable(start);

        long startTime = System.currentTimeMillis();
        log.info("实时路径模块开始查询数据库,现在为:{}",startTime);
        List<TaxiLocation> list = routeDao.findTaxiLocation(table,start,end);
        long endTime = System.currentTimeMillis();
        log.info("实时路径模块查询完毕，共用时{}",endTime - startTime);
        List<TaxiLocation> result = new ArrayList<>();
        for (TaxiLocation taxi :
                list) {
            if ( taxiLocation.getLicenseplateno().equals(taxi.getLicenseplateno())){
                GCJ02_WGS84.wgs84_To_Gcj02(taxi);
                result.add(taxi);
            }
        }
        log.info("用户查询的数据的长度为:{}",result.size());
        if(result.isEmpty()){
            throw new CheckException("该车没有信息");
        }

        list.clear();

        return result;

    }
}
