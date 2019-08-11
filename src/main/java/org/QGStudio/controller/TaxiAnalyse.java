package org.QGStudio.controller;

import org.QGStudio.dtos.ResultBean;
import org.QGStudio.service.AnalyseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName TaxiAnalyse
 * @Description TODO
 * @Author huange7
 * @Date 2019-08-10 16:04
 * @Version 1.0
 */

@RestController
@RequestMapping("/analyse")
public class TaxiAnalyse {

    @Autowired
    private AnalyseService analyseService;

    @PostMapping("/analyseTaxi")
    public ResultBean<?> analyseTaxi(double maxLon, double minLon,double maxLat, double minLat, String time){
        analyseService.analyseTaxi(maxLon,minLon,maxLat,minLat,time);
        return null;
    }
}
