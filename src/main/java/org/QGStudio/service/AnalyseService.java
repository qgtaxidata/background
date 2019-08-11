package org.QGStudio.service;

import java.util.List;

/**
 * @InterfaceName AnalyseService
 * @Description 分析出租车
 * @Author huange7
 * @Date 2019-08-10 16:08
 * @Version 1.0
 */
public interface AnalyseService {

    /**
     * @title : 分析taxi数据      
     * @param :[maxLon, minLon, maxLat, minLat, time]
     * @return : java.util.List
     * @author : huange7
     * @date : 2019-08-10 16:24
     */
    List analyseTaxi(double maxLon, double minLon,double maxLat, double minLat, String time);
}
