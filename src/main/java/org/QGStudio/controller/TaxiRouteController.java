package org.QGStudio.controller;

import lombok.extern.log4j.Log4j2;
import org.QGStudio.dtos.ResultBean;
import org.QGStudio.model.Location;
import org.QGStudio.model.TaxiLocation;
import org.QGStudio.service.TaxiRouteService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

/**
 * @Description: $
 * @Param: $
 * @return: $
 * @author: SheledonPeng
 * @Date: $
 */

@RestController("/taxiRoute")
@RequestMapping("/taxiRoute")
@CrossOrigin
@Log4j2
public class TaxiRouteController {


    @Autowired
    private TaxiRouteService taxiRouteService;

    @PostMapping("/findTaxi")
    public ResultBean<?> findTaxi(@RequestBody Location location) throws ParseException {

        return new ResultBean<>(taxiRouteService.findTaxi(location));
    }

    @PostMapping("/findRoute")
    public ResultBean<?> findRoute(@RequestBody TaxiLocation taxiLocation) throws ParseException {

        log.info(taxiLocation);
        return new ResultBean<>(taxiRouteService.findRoute(taxiLocation));
    }
}