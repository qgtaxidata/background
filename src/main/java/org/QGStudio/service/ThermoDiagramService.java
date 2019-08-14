package org.QGStudio.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.QGStudio.model.Location;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * @InterfaceName ThermoDiagramService
 * @Description TODO
 * @Author huange7
 * @Date 2019-08-09 8:59
 * @Version 1.0
 */
public interface ThermoDiagramService {
   
    /**
     * @title : 根据坐标找出当前坐标的热力图      
     * @param :[location]
     * @return : java.util.List
     * @author : huange7
     * @date : 2019-08-10 12:38
     */
    List findHeatMap(Location location) throws ParseException;
    
    /**
     * @title : 根据经纬度的限制找出热力图      
     * @param :[maxLon, minLon, maxLat, minLat,time]
     * @return : java.util.List
     * @author : huange7
     * @date : 2019-08-10 12:38
     */
    List findHeadMap(double maxLon,double minLon,double maxLat, double minLat,String time) throws ParseException;
    
    /**
     * @title : 根据地区id查出当前地区的热力图      
     * @param :[area,time]
     * @return : java.util.List
     * @author : huange7
     * @date : 2019-08-10 23:06
     */
    List findAreaMap(int area,String time);

    /**
     * @title : 用户请求未来热力图，此接口用于发送数据给数据挖掘
     * @param :[area, time]
     * @return : java.util.List
     * @author : huange7
     * @date : 2019-08-11 7:49
     */
    List findMapNow(int area,String time);

    /**
     * @title : 获取未来热力图
     * @param :[nowTime, futureTime, area,algorithm]
     * @return : java.util.List
     * @author : huange7
     * @date : 2019-08-12 16:58
     */
    Object findFutureMap(String nowTime , String futureTime, int area, int algorithm) throws IOException;
}
