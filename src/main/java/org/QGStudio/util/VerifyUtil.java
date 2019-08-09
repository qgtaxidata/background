package org.QGStudio.util;

import org.QGStudio.model.Location;

import java.util.Collection;

/**
 * @Description :
 * @Param :
 * @Return :
 * @Author : SheldonPeng
 * @Date : 2019-08-06
 */
public class VerifyUtil {


    /**
     * @Description : 判断字符串是否为空
     * @Param : [string]
     * @Return : boolean
     * @Author : SheldonPeng
     * @Date : 2019-08-06
     */

    public static boolean isEmpty(String string){

        return  (null == string  || "".equals(string) );
    }

    /**
     * @Description : 判断对象是否为空
     * @Param : [object]
     * @Return : boolean
     * @Author : SheldonPeng
     * @Date : 2019-08-07
     */
    public static boolean isNull(Object object){

        return null == object;
    }

    /**
     * @Description : 判断集合是否为空
     * @Param : [collection]
     * @Return : boolean
     * @Author : SheldonPeng
     * @Date : 2019-08-07
     */
    public static boolean isEmpty(Collection<?> collection){

        return  ( null == collection || collection.size() == 0 );
    }

    /**
     * @Description : 判断location对象的成员变量是否为空
     * @Param : [location]
     * @Return : boolean
     * @Author : SheldonPeng
     * @Date : 2019-08-09
     */
    public static boolean locationIsEmpty(Location location) {
        if (null == location.getLongitude() || null == location.getLatitude()
            || null == location.getTime()) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(Integer integer) {
       return (null == integer);
    }



}
