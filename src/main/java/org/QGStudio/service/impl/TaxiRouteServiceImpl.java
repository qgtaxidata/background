package org.QGStudio.service.impl;

import ch.hsr.geohash.GeoHash;
import lombok.extern.log4j.Log4j2;
import org.QGStudio.dao.RouteDao;
import org.QGStudio.exception.CheckException;
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
    public List findTaxi(Location location) throws ParseException {

        if (VerifyUtil.isNull(location)) {
            throw new CheckException("请输入正确的经纬度坐标!");

        }
        if (VerifyUtil.locationIsEmpty(location)) {
            throw new CheckException("请输入正确的经纬度坐标!");
        }

        // todo 使用转换成火星坐标再获取GEOHash块
        // 获得相邻的九个GEOHash块
        GeoHash[] neighborGeohash = GeoHashUtil.findNeighborGeohash(location);

        List<TaxiLocation> taxiLocationList = new ArrayList<>();
        // 获取开始时间和结束时间
        Date startTime = null;
        Date endTime = null;

        startTime = format.parse(location.getTime());
        endTime = format.parse(location.getTime());

        endTime.setMinutes(endTime.getMinutes() + 1);
        // 获得表名
        String table = TableUtil.getGpsdataTable(startTime);

        // 获得9个GEOHash块中的车辆信息
        for (int i = 0; i < neighborGeohash.length; i++) {

            taxiLocationList.addAll(routeDao.findTaxi(table,neighborGeohash[i].toBase32() + "%" ,startTime,endTime));
        }

        log.info("查询的返回结果数量为：{}",taxiLocationList.size());
        return taxiLocationList;
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
        Map<Integer,List<TaxiLocation>> map = new HashMap<>();

        // 建立结果集
        for (int i = 0; i < 6; i++) {

            map.put(i,new ArrayList<>());
        }

        // 建立6条线程 以10分钟为粒度对一小时的车辆进行检索
        for (int i = 0; i < 6; i++) {

            Date start = new Date();
            Date end = new Date();
            start.setTime(startTime.getTime());
            end.setTime(endTime.getTime());

            start.setMinutes(start.getMinutes() + 10 * i);
            end.setMinutes(end.getMinutes() + 10 * ( i + 1));

            log.info("开始时间为:{}",startTime);
            executor.execute(new RouteRunnable(countDownLatch,map.get(i),start,end,table,taxiLocation.getLicenseplateno()));
        }

        try {
            if ( ! countDownLatch.await(60,TimeUnit.SECONDS)) {
                throw new CheckException("查询超时,请稍后重试");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new CheckException("查询超时,请稍后重试");
        }

        // 筛选数据
        long time1 = System.currentTimeMillis();
        log.info("开始处理:{}",time1);

        List<TaxiLocation> routs = new ArrayList<>();
        for (int i = 0; i < 6; i++) {

            routs.addAll(map.get(i));
        }
        for (TaxiLocation taxi :
                routs) {
            GCJ02_WGS84.wgs84_To_Gcj02(taxi);
        }

        long time2 = System.currentTimeMillis();
        log.info("处理结束：{}",time2);
        return routs;

    }
}
