package org.QGStudio.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.QGStudio.dtos.ResultBean;
import org.QGStudio.model.Location;
import org.QGStudio.service.RoutePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @ClassName RoutePlanController
 * @Description
 * @Author huange7。guopei
 * @Date 2019-08-09 15:23
 * @Version 1.0
 */
@RestController
@RequestMapping("/route")
@CrossOrigin
public class RoutePlanController {

    @Autowired
    private RoutePlanService routePlanService;

    @PostMapping("/findAll")
    public ResultBean<?> findAllRoute(@RequestBody Location start,@RequestBody Location end) throws JsonProcessingException {
        return new ResultBean<>(routePlanService.findAllRoutes(start.getLongitude(),start.getLatitude(),
                end.getLongitude(),end.getLatitude()));
    }

    /**
     * 查找单条路线
     * @param time
     * @param routeId
     * @return
     */
    @PostMapping("/findSingle")
    public ResultBean<?> findSingleRoute(String time, int routeId) throws JsonProcessingException {
        return new ResultBean<>(routePlanService.findSingleRoute(time, routeId));
    }



}
