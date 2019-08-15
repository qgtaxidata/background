package org.QGStudio.dao;

import org.QGStudio.model.TaxiLocation;
import org.apache.ibatis.annotations.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @Description : 路径查询的dao层
 * @Param :
 * @Return :
 * @Author : SheldonPeng
 * @Date : 2019-08-10
 */
@Repository
@Scope("prototype")
public interface RouteDao {


    /**
     * @Description : 查找一块区域内的车辆信息
     * @Param : [table, geoHash, startTime, endTime]
     * @Return : java.util.List<org.QGStudio.model.TaxiLocation>
     * @Author : SheldonPeng
     * @Date : 2019-08-10
     */
    @Select("select distinct LICENSEPLATENO,GPS_TIME from ${table} where GPS_Time between #{startTime} and #{endTime} " +
            "AND LONGITUDE between #{minLon} and #{maxLon} AND LATITUDE between #{minLat} and " +
            "#{maxLat} limit 20")
    @Results(id = "TaxiMap",value = {
            @Result(column = "GPS_Time",property = "time"),
            @Result(column = "LICENSEPLATENO" , property = "licenseplateno")
    })
    List<TaxiLocation> findTaxi(@Param("table") String table, @Param("startTime")Date startTime,
                                @Param("endTime") Date endTime , @Param("maxLon") Double maxLon ,
                                @Param("minLon") Double minLon,@Param("maxLat") Double maxLat,
                                @Param("minLat") Double minLat);

    /**
     * @Description : 根据车牌号，寻找一段时间此车的经纬度集合
     * @Param : [table, startTime, endTime, licenseplateno]
     * @Return : java.util.List<org.QGStudio.model.TaxiLocation>
     * @Author : SheldonPeng
     * @Date : 2019-08-10
     */
    @Select("select LONGITUDE,LATITUDE,LICENSEPLATENO from ${table} where GPS_TIME between #{startTime} and #{endTime}")
    @Results(id = "routeMap", value = {
            @Result(column = "LICENSEPLATENO", property = "licenseplateno"),
            @Result(column = "LONGITUDE" , property = "longitude"),
            @Result(column = "LATITUDE" , property = "latitude")
    })
    List<TaxiLocation> findTaxiLocation(@Param("table") String table, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
