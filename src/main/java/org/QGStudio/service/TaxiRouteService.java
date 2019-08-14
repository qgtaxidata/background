package org.QGStudio.service;

import org.QGStudio.model.Location;
import org.QGStudio.model.TaxiLocation;

import java.text.ParseException;
import java.util.List;

/**
 * @Description : 出租车道路可视化的服务类
 * @Param :
 * @Return :
 * @Author : SheldonPeng
 * @Date : 2019-08-10
 */
public interface TaxiRouteService {


    /**
     * @Description : 查找一定范围内的出租车(经纬度转化为五级块)
     * @Param : []
     * @Return : java.util.List
     * @Author : SheldonPeng
     * @Date : 2019-08-10
     */
    List findTaxi(String time , int area) throws ParseException;


    /**
     * @Description : 获得一辆车一小时的路径情况
     * @Param : [taxiLocation]
     * @Return : java.util.List<org.QGStudio.model.TaxiLocation>
     * @Author : SheldonPeng
     * @Date : 2019-08-10
     */
    List<TaxiLocation> findRoute(TaxiLocation taxiLocation) throws ParseException;


    List<TaxiLocation> findLiveRoute( TaxiLocation taxiLocation) throws ParseException;

}
