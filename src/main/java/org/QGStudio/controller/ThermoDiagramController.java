package org.QGStudio.controller;

import org.QGStudio.dtos.ResultBean;
import org.QGStudio.model.Location;
import org.QGStudio.service.ThermoDiagramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;

/**
 * @ClassName ThermoDiagramController
 * @Description
 * @Author huange7
 * @Date 2019-08-09 10:34
 * @Version 1.0
 */
@RestController()
@RequestMapping("/thermoDiagram")
@CrossOrigin
public class ThermoDiagramController {

    @Autowired
    ThermoDiagramService thermoDiagramService;

    /**
     * @title : 利用中心坐标点进行周围五级geohash块的搜索
     * @param :[location]
     * @return : org.QGStudio.dtos.ResultBean<?>
     * @author : huange7
     * @date : 2019-08-20 9:13
     */
    // 该方法已被弃用
    @PostMapping("/getMap")
    public ResultBean<?> getMap(@RequestBody Location location) throws ParseException {
        System.out.println(location);
        return new ResultBean<>(thermoDiagramService.findHeatMap(location));
    }

    /**
     * @title : 根据四个点定下矩形，计算矩形内七级geohash块的权重
     * @param :[maxLon, minLon, maxLat, minLat, time]
     * @return : org.QGStudio.dtos.ResultBean<?>
     * @author : huange7
     * @date : 2019-08-20 9:14
     */
    // 该方法已被弃用
    @PostMapping("/getHeatMap")
    public ResultBean<?> getHeatMap(double maxLon, double minLon,double maxLat, double minLat, String time) throws ParseException {
        return new ResultBean<>(thermoDiagramService.findHeadMap(maxLon, minLon, maxLat, minLat, time));
    }

    @RequestMapping("/getAreaMap")
    public ResultBean<?> getAreaMap(@RequestParam("area") int area,@RequestParam("time") String time){
        return new ResultBean<>(thermoDiagramService.findMapNow(area,time));
    }

    @RequestMapping("/getFutureMap")
    public ResultBean<?> getFutureMap(@RequestParam("nowTime") String nowTime,@RequestParam("futureTime") String futureTime,@RequestParam("area") int area,
                                      @RequestParam("algorithm") int algorithm) throws IOException {

        return new ResultBean<>(thermoDiagramService.findFutureMap(nowTime,futureTime,area,algorithm));
    }

 }
