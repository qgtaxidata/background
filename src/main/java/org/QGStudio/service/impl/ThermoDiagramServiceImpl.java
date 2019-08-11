package org.QGStudio.service.impl;

import ch.hsr.geohash.GeoHash;
import lombok.extern.log4j.Log4j2;
import org.QGStudio.dao.LocationDao;
import org.QGStudio.exception.CheckException;
import org.QGStudio.model.Location;
import org.QGStudio.model.LocationWithHeight;
import org.QGStudio.model.Point;
import org.QGStudio.runnable.FindGeohashRUnnable;
import org.QGStudio.service.ThermoDiagramService;
import org.QGStudio.util.GeoHashUtil;
import org.QGStudio.util.TableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

/**
 * @ClassName ThermoDiagramServiceImpl
 * @Description 热力图业务层
 * @Author huange7
 * @Date 2019-08-09 9:46
 * @Version 1.0
 */
@Service
@Log4j2
public class ThermoDiagramServiceImpl implements ThermoDiagramService {
    @Autowired
    private LocationDao locationDao;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /**
     * @param :[location]
     * @return : java.util.List
     * @title : 根据经纬度找到对应的热力图
     * @author : huange7
     * @date : 2019-08-09 14:23
     */
    @Override
    public List findHeatMap(Location location) throws ParseException {

        log.info("the request location is "+location);
        // 根据该坐标到当相邻的八块geohash块
        // 这里返回的是9块，包含它本身
        GeoHash[] geoHashes = GeoHashUtil.findNeighborGeohash(location);
        List<LocationWithHeight> locations = new Vector();
        //计数器
        CountDownLatch countDownLatch = new CountDownLatch(geoHashes.length);
        ExecutorService e = new ThreadPoolExecutor(4, 16, 6000, TimeUnit.MINUTES, new SynchronousQueue<>());

        // 遍历geohash块，并在数据库中查找该geohash块对应的子geohash块
        for (GeoHash geoHash : geoHashes) {

            // 设置时间格式为 yyyy-MM-dd HH:mm:ss ,对应数据库的GPS_TIME字段的格式
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            // 调用location的时间，并计算此时的终止时间
            Date startDate = format.parse(location.getTime());
            Date endDate = format.parse(location.getTime());
            // 这里设置间隔为5秒，后期考虑要不要进行修改
            // TODO 这里设置时间间隔为5秒，建议可调整该值
            endDate.setSeconds(endDate.getSeconds() + 15);

            // 根据开始的时间查看对应的数据库表
            String table = TableUtil.getTable(startDate);
            // 将查到的数据整合到List中
            log.info("开启一个线程查询热力图数据");
            FindGeohashRUnnable findGeohashRUnnable = new FindGeohashRUnnable(table, startDate, endDate,
                    geoHash.getBoundingBox(), locations, locationDao, countDownLatch);
//            threadPoolTaskExecutor.execute(findGeohashRUnnable);
            new Thread(findGeohashRUnnable).start();
        }
        // 记录日志，用户执行查询9个geohash5 成功

        try {
            //20秒超时
            countDownLatch.await(20, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
            throw new CheckException("查询热力图失败");
        } finally {
            e.shutdownNow();
        }
        //统计权重
        Map<String, Integer> map = new HashMap();
        for (LocationWithHeight locationWithHeight : locations) {
            if (!map.containsKey(locationWithHeight.getGeohash())) {
                map.put(locationWithHeight.getGeohash(), 1);
            } else {
                map.put(locationWithHeight.getGeohash(), map.get(locationWithHeight.getGeohash()) + 1);
            }
        }

    /*    //计算坐标
        Set<String> keys = map.keySet();
        List<Point> points = new LinkedList<>();
        for (String k : keys) {
            Point point = new Point();
            point.setLat(GeoHashUtil.geohash2Location(k).getLatitude());
            point.setLng(GeoHashUtil.geohash2Location(k).getLongitude());
            point.setCount(map.get(k));
            points.add(point);
        }*/


//        // 遍历找到的七级geohash块，并将该块的中心点的经纬度设置为该块代表的经纬度
//        for (LocationWithHeight locationWithHeight : locations) {
//            locationWithHeight.setLatitude(GeoHashUtil.geohash2Location(locationWithHeight.getGeohash()).getLatitude());
//            locationWithHeight.setLongitude(GeoHashUtil.geohash2Location(locationWithHeight.getGeohash()).getLongitude());
//        }
        log.info("用户成功查询所有的geohash块并返回对应的坐标点和权重");
        return null;
        // todo 还是要改
    }
}
