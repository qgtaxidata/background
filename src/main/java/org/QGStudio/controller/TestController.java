package org.QGStudio.controller;

import ch.hsr.geohash.BoundingBox;
import org.QGStudio.dao.LocationDao;
import org.QGStudio.dao.TestDao;
import org.QGStudio.dtos.ResultBean;
import org.QGStudio.model.LocationWithHeight;
import org.QGStudio.model.Point;
import org.QGStudio.service.TestService;
import org.QGStudio.util.GeoHashUtil;
import org.QGStudio.util.TableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private TestDao testDao;

    @RequestMapping("/testUser")
    public ResultBean<?> testUser(@RequestParam int userId) {

        return new ResultBean<>(testService.testUser(userId));
    }

    @Autowired
    private LocationDao locationDao;

    @RequestMapping("/test")
    public ResultBean<?> testFind() throws ParseException {


        // 根据开始的时间查看对应的数据库表
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 调用location的时间，并计算此时的终止时间
        Date startDate = format.parse("2017-02-01 19:00:00");
        Date endDate = format.parse("2017-02-01 19:00:00");
        String table = TableUtil.getTable(startDate );
        // 这里设置间隔为5秒，后期考虑要不要进行修改
        // TODO 这里设置时间间隔为5秒，建议可调整该值
        endDate.setSeconds(endDate.getSeconds() + 60);
        System.out.println("startdate = "+startDate+" enddate = "+endDate);
        // 将查到的数据整合到List中
        BoundingBox boundingBox = new BoundingBox(24.89735653390902, 21.59267856894609, 115.08720445871353,
                111.96934557199478);
        List<LocationWithHeight> locations = locationDao.findLocation(table, boundingBox.getMaxLon(),
                boundingBox.getMinLon(), boundingBox.getMaxLat(), boundingBox.getMinLat(),
                startDate,endDate);


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
            point.setLat(GeoHashUtil.geohash2Location(k).getLatitude());
            point.setLng(GeoHashUtil.geohash2Location(k).getLongitude());
            point.setCount(map.get(k));
            points.add(point);
        }


        System.out.println("用户成功查询所有的geohash块并返回对应的坐标点和权重");

        return new ResultBean<>(points);
    }
}
