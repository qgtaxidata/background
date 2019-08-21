package org.QGStudio.controller;

import org.QGStudio.dtos.ResultBean;
import org.QGStudio.service.AnalyseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName TaxiAnalyse
 * @Description 出租车的分析
 * @Author huange7
 * @Date 2019-08-10 16:04
 * @Version 1.0
 */

@RestController
@RequestMapping("/analyse")
@CrossOrigin
public class TaxiAnalyse {

    @Autowired
    private AnalyseService analyseService;

    @PostMapping("/analyseTaxi")
    public ResultBean<?> analyseTaxi(double maxLon, double minLon,double maxLat, double minLat, String time){
        analyseService.analyseTaxi(maxLon,minLon,maxLat,minLat,time);
        return null;
    }

    @RequestMapping("/billboard")
    public ResultBean<?> analyseBillboard(@RequestParam("area") int area, @RequestParam("targetTime") int targetTime, @RequestParam("targetDay")int targetDay){
        return new ResultBean<>(analyseService.analyseBillboard(area, targetTime, targetDay));
    }

    @RequestMapping("/income")
    public ResultBean<?> analyseIncome(@RequestParam("area")int area, @RequestParam("date")String date){
        return new ResultBean<>(analyseService.analyseIncome(area,date));
    }

    @RequestMapping("/vehicleUtilizationRate")
    public ResultBean<?> getVehicleUtilizationRate(@RequestParam("area")int area, @RequestParam("date")String date){
        return new ResultBean<>(analyseService.getVehicleUtilizationRate(area, date));
    }

    @GetMapping("/abnormalTaxiAnalysis")
    public ResultBean<?> abnormalTaxiAnalysis() {
        return new ResultBean<>(analyseService.abnormalTaxiAnalysis());
    }
}
