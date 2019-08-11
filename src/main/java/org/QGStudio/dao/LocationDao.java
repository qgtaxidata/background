package org.QGStudio.dao;

import org.QGStudio.model.LocationWithHeight;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @InterfaceName LocationDao
 * @Description
 * @Author huange7
 * @Date 2019-08-09 9:23
 * @Version 1.0
 */

@Repository
public interface LocationDao {

    @Select("select geohash from ${table} where  GPS_TIME between #{startTime} and #{endTime} AND  longitude between #{minLon} and #{maxLon} and latitude between #{minLat} and #{maxLat}")
    @Results(id="locationMap",value = {
            @Result(column = "geohash", property = "geohash"),
            @Result(column = "height", property = "count")
    })
    List<LocationWithHeight> findLocation(@Param("table") String table, @Param("maxLon") double maxLon, @Param("minLon")double
            minLon, @Param("maxLat")double maxLat, @Param("minLat")double minLat, @Param("startTime") Date startTime, @Param("endTime")Date endTime);
}
