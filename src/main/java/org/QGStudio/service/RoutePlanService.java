package org.QGStudio.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.ObjectOutputStream;


/**
 * @InterfaceName RoutePlanService
 * @Description TODO
 * @Author huange7, guo
 * @Date 2019-08-09 15:52
 * @Version 1.0
 */
public interface RoutePlanService {

    String findAllRoutes(double startLng,double startLat,double endLng,double endLat) throws JsonProcessingException;


    /**
     * 查询单条路线
     * @param time
     * @param routeId
     * @return
     */
    String findSingleRoute(String time, int routeId) throws JsonProcessingException;

    /**
     * 获取路线
     * @param lonOrigin
     * @param lanOrigin
     * @param lonDestination
     * @param lanDestination
     * @return
     */
    Object getRoute(Float lonOrigin, Float lanOrigin, Float lonDestination, Float lanDestination) throws JsonProcessingException;

}
