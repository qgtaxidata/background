package org.QGStudio.util;

import lombok.extern.log4j.Log4j2;
import java.util.Date;
import java.util.Objects;

/**
 * @ClassName TableUtil
 * @Description TODO
 * @Author huange7
 * @Date 2019-08-09 9:59
 * @Version 1.0
 */
@Log4j2
public class TableUtil {

    public static String getGpsdataTable(Date date) {
        return "gpsdata" + date.getDate();
    }

    public static String getOperateHisTable(Date date) {
        if (date.after(Objects.requireNonNull(TimeUtil.StrToDate("2017-03-01 00:00:00")))) {
            return "operate_his" + (date.getDate() + 28);
        } else {
            return "operate_his" + date.getDate();
        }
    }

}
