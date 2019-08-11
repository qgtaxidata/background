package org.QGStudio.service;

import com.fasterxml.jackson.core.JsonProcessingException;



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



}
