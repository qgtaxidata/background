package org.QGStudio.dao;

import org.QGStudio.model.LocationWithHeight;
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


    @Select("select longitude , latitude , geohash from #{table} where GPS_TIME > #{startTime}  AND GPS_TIME < #{endTime} and GEOHASH like #{geohash}")
    @Results(id="locationMap",value = {
            @Result(column = "longitude", property = "longitude"),
            @Result(column = "latitude", property = "latitude"),
            @Result(column = "geohash", property = "geohash")
    })
    List<LocationWithHeight> findLocation(String table,String startTime,String endTime,String geohash);
}
