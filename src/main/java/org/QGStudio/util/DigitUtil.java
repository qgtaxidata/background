package org.QGStudio.util;

import org.QGStudio.model.Location;

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

        String longitude = String.valueOf(location.getLongitude());
        String latitude = String.valueOf(location.getLatitude());
        int length_long = longitude.substring(longitude.lastIndexOf(".") + 1).length();
        int length_la = latitude.substring(latitude.lastIndexOf(".") + 1).length();

        while (length_long < 6) {
            longitude = longitude + "0";
            length_long++;
        }

        while (length_la < 6) {
            latitude = latitude + "0";
            length_la++;
        }

        location.setLongitude(Double.parseDouble(longitude));
        location.setLatitude(Double.parseDouble(latitude));
        return location;
    }
}
