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

    @PostMapping("/getMap")
    public ResultBean<?> getMap(@RequestBody Location location) throws ParseException {
        System.out.println(location);
        return new ResultBean<>(thermoDiagramService.findHeatMap(location));
    }

    @PostMapping("/getHeatMap")
    public ResultBean<?> getHeatMap(double maxLon, double minLon,double maxLat, double minLat, String time) throws ParseException {
        return new ResultBean<>(thermoDiagramService.findHeadMap(maxLon, minLon, maxLat, minLat, time));
    }

    @PostMapping("/getAreaMap")
    public ResultBean<?> getAreaMap(@RequestParam("area") int area,@RequestParam("time") String time){
        return new ResultBean<>(thermoDiagramService.findMapNow(area,time));
    }

    @PostMapping("/getFutureMap")
    public ResultBean<?> getFutureMap(@RequestParam("nowTime") String nowTime,@RequestParam("futureTime") String futureTime,@RequestParam("area") int area,
                                      @RequestParam("algorithm") int algorithm) throws IOException {

        return new ResultBean<>(thermoDiagramService.findFutureMap(nowTime,futureTime,area,algorithm));
    }

 }
