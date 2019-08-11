package org.QGStudio.controller;

import ch.hsr.geohash.BoundingBox;
import ch.hsr.geohash.GeoHash;
import ch.hsr.geohash.WGS84Point;
import org.QGStudio.dao.LocationDao;
import org.QGStudio.dao.TestDao;
import org.QGStudio.dtos.ResultBean;
import org.QGStudio.model.*;
import org.QGStudio.service.TestService;
import org.QGStudio.util.GCJ02_WGS84;
import org.QGStudio.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @Description: $
 * @Param: $
 * @return: $
 * @author: SheledonPeng
 * @Date: $
 */

@RestController("/test")
@RequestMapping("/test")
@CrossOrigin
public class TestController {

    // TODO 写实体类判断类
    @Autowired
    private TestService testService;

    @Autowired
    private LocationDao locationDao;

    @Autowired
    private TestDao testDao;

    @RequestMapping("/testUser")
    public ResultBean<?> testUser(@RequestParam int userId){

        return new ResultBean<>(testService.testUser(userId));
    }

    @RequestMapping("/test")
    public ResultBean<?> testFind(@RequestBody Location location, HttpServletResponse httpServletResponse){


        return new ResultBean<>();
    }

    @RequestMapping("/haha")
    public ResultBean<?> testSize(){
        Location location ;
        WGS84Point wgs84Point = new WGS84Point(23.11416,113.24082);
        location = (GCJ02_WGS84.wgs84_To_Gcj02(wgs84Point.getLatitude(),wgs84Point.getLongitude()));
        GeoHash geoHash = GeoHash.withCharacterPrecision(location.getLatitude(),location.getLongitude(),5);
        BoundingBox boundingBox = geoHash.getBoundingBox();
        System.out.println("经度差："+(boundingBox.getMaxLon()-boundingBox.getMinLon()));
        System.out.println("纬度差："+(boundingBox.getMaxLat()-boundingBox.getMinLat()));
        return  null;
        }

    @RequestMapping("/testxixi")
    public ResultBean<?> test(){
        List<Location> locations = new LinkedList<>();
        for (String s:
                AreaLocation.TIANHE_DISTRICT) {
            GeoHash geoHash = GeoHash.fromGeohashString(s);
            BoundingBox boundingBox = geoHash.getBoundingBox();
            Date startTime = TimeUtil.StrToDate("2017-02-04 15:00:00");
            Date endTime = TimeUtil.StrToDate("2017-02-04 15:00:15");
            locations.addAll(locationDao.findLocation("gpsdata4",boundingBox.getMaxLon(),boundingBox.getMinLon(),boundingBox.getMaxLat(),boundingBox.getMinLat(),startTime,endTime));
        }
        return new ResultBean<>(locations);
    }
}
