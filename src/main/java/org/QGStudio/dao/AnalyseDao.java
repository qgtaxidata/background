package org.QGStudio.dao;

import org.QGStudio.model.Taxi;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @InterfaceName AnalyseDao
 * @Description 查询区域出租车
 * @Author huange7
 * @Date 2019-08-10 16:31
 * @Version 1.0
 */

@Repository
public interface AnalyseDao {

    @Select("select licenseplateno from ${table} where longitude between #{minLon} and #{maxLon} and latitude between #{minLat} and #{maxLat} " +
            "and gps_time BETWEEN #{startTime} and #{endTime} ")
    @Results(id="paltenoMap",value = {
            @Result(column = "licenseplateno", property = "plateno")
    })
    List<Taxi> findPlateno(@Param("table")String table, @Param("maxLon")double maxLon, @Param("minLon")double minLon, @Param("maxLat")double maxLat,
                           @Param("minLat")double minLat, @Param("startTime") Date startTime, @Param("endTime")Date endTime);


    @Select("select WORK_BEGIN_TIME from ${table} where PLATENO = #{plateno}")
    @Results(id="taxiMap",value = {
            @Result(column = "work_begin_time",property = "workBeginTime")
    })
    Taxi findTaxi(@Param("table") String table ,@Param("plateno") String plateno);

}
