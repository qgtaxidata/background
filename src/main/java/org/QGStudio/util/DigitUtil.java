package org.QGStudio.util;

import org.QGStudio.model.Location;

import java.text.DecimalFormat;

/**
 * @description 将经纬度的小数点位数设定为6
 * @author < a href=" ">郭沛</ a>
 * @date 2019-08-08 20:47
 */
public class DigitUtil {

    /**
     * 将经纬度的小数点位数设定为6
     */
    public static Location checkLocationDigit(Location location) {

        String longitude = new DecimalFormat("0.000000").format(location.getLongitude());
        String latitude = new DecimalFormat("0.000000").format(location.getLatitude());
        location.setLongitude(Double.parseDouble(longitude));
        location.setLatitude(Double.parseDouble(latitude));
        return location;
    }
}
