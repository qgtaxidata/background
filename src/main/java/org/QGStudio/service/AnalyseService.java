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

    /**
     * @title : 根据地区，时间推送广告牌位置
     * @param :[area, targetTime, targetDay]
     * @return : java.util.List
     * @author : huange7
     * @date : 2019-08-18 15:16
     */
    Object analyseBillboard(int area, int targetTime, int targetDay);

    /**
     * @title : 根据地区，日期分析收入
     * @param :[area, date]
     * @return : java.lang.Object
     * @author : huange7
     * @date : 2019-08-18 17:17
     */
    Object analyseIncome(int area, String date);
}
