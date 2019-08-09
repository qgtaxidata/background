package org.QGStudio.dao;

import org.QGStudio.model.LocationWithHeight;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @InterfaceName LocationDao
 * @Description TODO
 * @Author huange7
 * @Date 2019-08-09 9:23
 * @Version 1.0
 */
public interface LocationDao {


    @Select("select  geohash,count(*) as height from ${table} where GPS_TIME > #{startTime} " +
            " AND GPS_TIME < #{endTime} and GEOHASH like #{geohash} group by geohash")
    @Results(id="locationMap",value = {
            @Result(column = "geohash", property = "geohash"),
            @Result(column = "height", property = "count")
    })
    List<LocationWithHeight> findLocation(@Param(value = "table") String table, @Param(value = "startTime")String startTime,@Param(value = "endTime") String endTime, @Param(value =
            "geohash")String geohash);
}
