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
@Log4j2
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
}
