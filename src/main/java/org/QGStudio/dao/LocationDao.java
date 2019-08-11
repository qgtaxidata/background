package org.QGStudio.dao;

import org.QGStudio.model.LocationWithHeight;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @InterfaceName LocationDao
 * @Description TODO
 * @Author huange7
 * @Date 2019-08-09 9:23
 * @Version 1.0
 */
@Scope("prototype")
@Repository
public interface LocationDao {

//    @Select("select geohash from ${table} where GEOHASH like #{geohash} and   " +
//            " GPS_TIME > #{startTime} AND GPS_TIME < #{endTime}")
//    @Results(id="locationMap",value = {
//            @Result(column = "geohash", property = "geohash"),
//    })
//    List<LocationWithHeight> findLocation(@Param(value = "table") String table,
//                                          @Param(value = "startTime")String startTime,
//                                          @Param(value = "endTime") String endTime,
//                                          @Param(value = "geohash")String geohash);

    @Select("select geohash from ${table} where  GPS_TIME between #{startTime} and #{endTime} AND  longitude between #{minLon} and #{maxLon} and latitude between #{minLat} and #{maxLat}")
    @Results(id = "locationMap", value = {
            @Result(column = "geohash", property = "geohash"),
            @Result(column = "height", property = "count")
    })
    List<LocationWithHeight> findLocation(@Param("table") String table, @Param("maxLon") double maxLon, @Param("minLon") double
            minLon, @Param("maxLat") double maxLat, @Param("minLat") double minLat, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
