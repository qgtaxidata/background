package org.QGStudio.dao;


import org.QGStudio.model.LocationWithHeight;
import org.QGStudio.model.User;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestDao {


    @Select("select * from userList where user_id = #{userId}")
    User testUser(int userId);

    @Select("select longitude , latitude , geohash from gpsdata1 where GPS_TIME > '2017-02-01 15:19:00'  AND GPS_TIME < '2017-02-01 15:19:05' and GEOHASH like #{geohash}")
    @Results(id = "map", value = {
            @Result(column = "longitude", property = "longitude"),
            @Result(column = "latitude", property = "latitude"),
            @Result(column = "geohash", property = "geohash")
    })
    List<LocationWithHeight> findLocation(String geohash);
}
