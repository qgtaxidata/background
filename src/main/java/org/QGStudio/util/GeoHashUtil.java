package org.QGStudio.util;

import ch.hsr.geohash.GeoHash;
import ch.hsr.geohash.WGS84Point;
import org.QGStudio.model.Location;
import org.QGStudio.model.LocationWithHeight;

/**
 * @ClassName GeoHashUtil
 * @Description TODO
 * @Author huange7
 * @Date 2019-08-08 21:03
 * @Version 1.0
 */
public class GeoHashUtil {

        public static String[] findNeighborGeohash(Location location){

            // 给出经纬度，设置geohash块的级别大小为5
            GeoHash center = GeoHash.withCharacterPrecision(location.getLatitude(), location.getLongitude(), 5);
            // 找到该点的上方的geohash块
            GeoHash top = center.getNorthernNeighbour();
            // 找到该点的下方的geohash块
            GeoHash bottom = center.getSouthernNeighbour();
            // 找到该点的左边的geohash块
            GeoHash left = center.getWesternNeighbour();
            // 找到该点的右边的geohash块
            GeoHash right = center.getEasternNeighbour();
            // 找到该点的左上方的geohash块
            GeoHash topLeft = top.getWesternNeighbour();
            // 找到该点的右上方的geohash块
            GeoHash topRight = top.getEasternNeighbour();
            // 找到该点的左下方的geohash块
            GeoHash bottomLeft = bottom.getWesternNeighbour();
            // 找到该点的右下方的geohash块
            GeoHash bottomRight = bottom.getEasternNeighbour();
            // 将它们转化为字符传并返回
            return new String[]{center.toString(),top.toString(),bottom.toString(),left.toString(),right.toString(),topLeft.toString(),topRight.toString()
            ,bottomLeft.toString(),bottomRight.toString()};
        }

        /**
         * @title : 将wgs84坐标转化为gcj02坐标
         * @param :[geohash]
         * @return : org.QGStudio.model.LocationWithHeight
         * @author : huange7
         * @date : 2019-08-09 14:32
         */
        public static LocationWithHeight geohash2Location(String geohash){
            WGS84Point wgs84Point = GeoHash.fromGeohashString(geohash).getPoint();
            LocationWithHeight location = GCJ02_WGS84.wgs84_To_Gcj02(wgs84Point.getLatitude(),wgs84Point.getLongitude());
            return location;
        }
}
