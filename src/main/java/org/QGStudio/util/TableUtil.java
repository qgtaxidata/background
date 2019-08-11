package org.QGStudio.util;

import javax.xml.crypto.Data;
import java.util.Date;

/**
 * @ClassName TableUtil
 * @Description TODO
 * @Author huange7
 * @Date 2019-08-09 9:59
 * @Version 1.0
 */
public class TableUtil {

    public static String getTable(Date date){
        return "gpsdata"+date.getDate();
    }
}
