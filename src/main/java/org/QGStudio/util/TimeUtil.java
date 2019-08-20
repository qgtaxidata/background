package org.QGStudio.util;

import lombok.extern.log4j.Log4j2;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description 对时间进行切割
 * @author < a href=" ">郭沛</ a>
 * @date 2019-08-08 21:36
 */
public class TimeUtil {

    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 对标准时间进行格式化为分钟精度的时间
     * @param time
     * @return
     */
    public static String formatTime(String time) throws ParseException {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        return dateFormat.format(dateFormat.parse(time));
    }

    public static Date StrToDate(String s) {
        try {
            return sdf.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @title : 判断时间是否跨天
     * @param :[startTime, endTime, time]
     * @return : int
     * @author : huange7
     * @date : 2019-08-20 9:13
     */
    public static Date isCrossDay(Date endTime, String time){
        Date newTime = TimeUtil.StrToDate(time);
        newTime.setHours(0);
        newTime.setMinutes(0);
        newTime.setSeconds(15);
        if (endTime.before(newTime)){
            newTime.setSeconds(0);
            return newTime;
        }else{
            return null;
        }
    }
}
