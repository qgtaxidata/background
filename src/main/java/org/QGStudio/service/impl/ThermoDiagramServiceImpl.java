package org.QGStudio.service.impl;

import ch.hsr.geohash.BoundingBox;
import ch.hsr.geohash.GeoHash;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.QGStudio.correspond.HttpClient;
import org.QGStudio.dao.LocationDao;
import org.QGStudio.exception.CheckException;
import org.QGStudio.model.AreaLocation;
import org.QGStudio.model.Location;
import org.QGStudio.model.LocationWithHeight;
import org.QGStudio.model.Point;
import org.QGStudio.runnable.NewFindGeoHashRunnable;
import org.QGStudio.service.ThermoDiagramService;
import org.QGStudio.util.*;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private ObjectFactory<HttpClient> clientBean;

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * @param :[location]
     * @return : java.util.List
     * @title : 根据经纬度找到对应的热力图
     * @author : huange7
     * @date : 2019-08-09 14:23
     */
    @Override
    public List findHeatMap(Location location) throws ParseException {

        GCJ02_WGS84.gcj02_To_Wgs84(location);
        // 根据该坐标到当相邻的八块geohash块
        // 这里返回的是9块，包含它本身


        GeoHash[] geoHashes = GeoHashUtil.findNeighborGeohash(location);
        List<LocationWithHeight> locations = new Vector();
        //计数器
        
        // 遍历geohash块，并在数据库中查找该geohash块对应的子geohash块
        for (GeoHash geoHash :
                geoHashes) {

            BoundingBox boundingBox = geoHash.getBoundingBox();

            // 调用location的时间，并计算此时的终止时间
            Date startDate = TimeUtil.StrToDate(location.getTime());
            Date endDate = TimeUtil.StrToDate(location.getTime());
            // 这里设置间隔为5秒，后期考虑要不要进行修改

            startDate.setSeconds(startDate.getSeconds() - 15);

            // 根据开始的时间查看对应的数据库表
            String table = TableUtil.getGpsdataTable(startDate);

            // 查询数据库中每个5级块中的七级块
            locations.addAll(locationDao.findLocation(table, boundingBox.getMaxLon(), boundingBox.getMinLon(),
                    boundingBox.getMaxLat(), boundingBox.getMinLat(), startDate, endDate));
        }

        // 调用计算权重方法并返回计算出的列表
        return statisticalWeight(locations);
    }

    /**
     * @param :[maxLon, minLon, maxLat, minLat]
     * @return : java.util.List
     * @title : 根据经纬度差给出热力图
     * @author : huange7
     * @date : 2019-08-10 13:10
     */
    @Override
    public List findHeadMap(double maxLon, double minLon, double maxLat, double minLat, String time) throws ParseException {

        Location locationMax = new Location().setLatitude(maxLat).setLongitude(maxLon);
        Location locationMin = new Location().setLatitude(minLat).setLongitude(minLon);

        GCJ02_WGS84.gcj02_To_Wgs84(locationMax);
        GCJ02_WGS84.gcj02_To_Wgs84(locationMin);


        Date startTime = TimeUtil.StrToDate(time);
        Date endTime = TimeUtil.StrToDate(time);
        // 这里设置间隔为5秒，后期考虑要不要进行修改
        startTime.setSeconds(startTime.getSeconds() - 15);


        // 根据开始的时间查看对应的数据库表
        String table = TableUtil.getGpsdataTable(startTime);

        // 查看dao层对应的七级块的点
        List<LocationWithHeight> locations = locationDao.findLocation(table, locationMax.getLongitude(), locationMin.getLongitude(), locationMax.getLatitude(),
                locationMin.getLatitude(), startTime, endTime);

        return statisticalWeight(locations);
    }

    @Override
    public List findAreaMap(int area, String time) {
        List<LocationWithHeight> locations = new LinkedList<>();


        Date startTime = TimeUtil.StrToDate(time);
        Date endTIme = TimeUtil.StrToDate(time);
        startTime.setSeconds(startTime.getSeconds() - 15);

        String table = TableUtil.getGpsdataTable(startTime);

        String[] geoHashes = AreaLocation.AREA_MAP.get(area);
        List<CountDownLatch> countDownLatches = new LinkedList<>();
        int count = geoHashes.length % 10 == 0 ? geoHashes.length / 10 : geoHashes.length / 10 + 1;
        CountDownLatch countDownLatch = new CountDownLatch(count);

        List geoHashList = new LinkedList();
        for (String geohash : AreaLocation.AREA_MAP.get(area)) {
            geoHashList.add(geohash);
            if (geoHashList.size() == 10) {
                NewFindGeoHashRunnable newFindGeoHashRunnable = new NewFindGeoHashRunnable(table, startTime, endTIme, geoHashList.toArray(), locations, countDownLatch);
                threadPoolTaskExecutor.execute(newFindGeoHashRunnable);
                geoHashList = new LinkedList();
            }

            long start = System.currentTimeMillis();
            log.info("开启时间,{}", start);
/*            locations.addAll(locationDao.findLocation(table,boundingBox.getMaxLon(),boundingBox.getMinLon(),boundingBox.getMaxLat(),
                    boundingBox.getMinLat(),startTime,endTIme));*/
            long end = System.currentTimeMillis();
            log.info("共用时,{}", end - start);
        }

        if (geoHashList.size() != 0) {
            NewFindGeoHashRunnable newFindGeoHashRunnable = new NewFindGeoHashRunnable(table, startTime, endTIme, geoHashList.toArray(), locations, countDownLatch);
            threadPoolTaskExecutor.execute(newFindGeoHashRunnable);
        }

        try {
            countDownLatch.await(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new CheckException("查询热力图失败");
        }

        if (countDownLatch.getCount() != 0) {
            throw new CheckException("查询热力图超时");
        }

        return statisticalWeight(locations);
    }

    @Override
    public List findMapNow(int area, String time) {

        Date startTime = TimeUtil.StrToDate(time);
        Date endTime = TimeUtil.StrToDate(time);
        startTime.setSeconds(startTime.getSeconds() - 15);

        String table = TableUtil.getGpsdataTable(startTime);

        log.info("用户查询地区{}", area);
        Location maxLocation = AreaLocation.MAX_MAP.get(area);
        Location minLocation = AreaLocation.MIN_MAP.get(area);
        log.info("查询的地区数据为:{}", AreaLocation.MAX_MAP.get(area).toString());
        List<LocationWithHeight> locations = locationDao.findLocation(table, maxLocation.getLongitude(), minLocation.getLongitude(),
                maxLocation.getLatitude(), minLocation.getLatitude(), startTime, endTime);

        return statisticalWeight(locations);
    }



    @Override
    public Object findFutureMap(String nowTime, String futureTime, int area, int algorithm) throws IOException {

        Date startTime = TimeUtil.StrToDate(nowTime);
        Date endTime = TimeUtil.StrToDate(nowTime);
        startTime.setSeconds(startTime.getSeconds() - 15);

        String table = TableUtil.getGpsdataTable(startTime);

        log.info("用户执行查询{}地区未来热力图", area);
        Location minLocation = AreaLocation.MIN_MAP.get(area);
        Location maxLocation = AreaLocation.MAX_MAP.get(area);
        log.info("查询的地区数据为:{}", AreaLocation.MAX_MAP.get(area).toString());
        List<LocationWithHeight> locations = locationDao.findLocation(table, maxLocation.getLongitude(), minLocation.getLongitude(),
                maxLocation.getLatitude(), minLocation.getLatitude(), startTime, endTime);
        Map<String,Object> heatMap = new HashMap<>();
        heatMap.put("nowTime",nowTime);
        heatMap.put("futureTime",futureTime);
        heatMap.put("area",area);
        heatMap.put("algorithm",algorithm);
        heatMap.put("heat",statisticalGeohashWeight(locations));
        String response = clientBean.getObject().doPostWithParam(heatMap,"http://192.168.1.101:8080//taxi/api/v1.0/FutureHeat");

        if (VerifyUtil.isEmpty(response)){
            throw new CheckException("网络通讯异常，请稍后重试!");
        }

        JavaType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, LocationWithHeight.class);
        List<LocationWithHeight> list = objectMapper.readValue(response,javaType);
        List<Point> points = new ArrayList<>();
        for (LocationWithHeight location:
             list) {
            Point point = new Point();
            point.setLat(GeoHash.fromGeohashString(location.getGeohash()).getPoint().getLatitude());
            point.setLng(GeoHash.fromGeohashString(location.getGeohash()).getPoint().getLongitude());
            point.setCount(location.getCount());
            GCJ02_WGS84.wgs84_To_Gcj02(point);
            points.add(point);
        }
        list.clear();
        return points;
    }
    
    /**
     * @title : 统计七级块的权重      
     * @param :[locations]
     * @return : java.util.List
     * @author : huange7
     * @date : 2019-08-14 7:58
     */

    private List statisticalGeohashWeight(List<LocationWithHeight> locations){

        // 统计geohash块的权重
        Map<String ,Integer> map = new HashMap<>();
        for(LocationWithHeight locationWithHeight : locations){
            if( !map.containsKey(locationWithHeight.getGeohash()) ){
                map.put(locationWithHeight.getGeohash(), 1);
            }else{
                map.put(locationWithHeight.getGeohash(), map.get(locationWithHeight.getGeohash()) + 1);
            }
        }
        Set<String> keys = map.keySet();
        List<LocationWithHeight> geohashes = new LinkedList<>();
        for (String k:
             keys) {
            LocationWithHeight locationWithHeight = new LocationWithHeight();
            locationWithHeight.setGeohash(k);
            locationWithHeight.setCount(map.get(k));
            geohashes.add(locationWithHeight);
        }
        log.info("用户查询成功！数据大小为{}", geohashes.size());
        return geohashes;
    }

    /**
     * @title : 统计七级块对应的中心点的权重      
     * @param :[locations]
     * @return : java.util.List
     * @author : huange7
     * @date : 2019-08-14 8:29
     */
    private List statisticalWeight(List<LocationWithHeight> locations) {
        // 记录日志，用户执行查询9个geohash5 成功
        //统计权重
        Map<String, Integer> map = new HashMap();
        for (LocationWithHeight locationWithHeight : locations) {
            if (!map.containsKey(locationWithHeight.getGeohash())) {
                map.put(locationWithHeight.getGeohash(), 1);
            } else {
                map.put(locationWithHeight.getGeohash(), map.get(locationWithHeight.getGeohash()) + 1);
            }
        }

        //计算坐标
        Set<String> keys = map.keySet();
        List<Point> points = new LinkedList<>();
        for (String k : keys) {
            Point point = new Point();
            point.setLat(GeoHash.fromGeohashString(k).getPoint().getLatitude());
            point.setLng(GeoHash.fromGeohashString(k).getPoint().getLongitude());
            GCJ02_WGS84.wgs84_To_Gcj02(point);
            point.setCount(map.get(k));
            points.add(point);
        }
        log.info("用户查询成功，数据大小为{}", points.size());
        return points;
    }
}
