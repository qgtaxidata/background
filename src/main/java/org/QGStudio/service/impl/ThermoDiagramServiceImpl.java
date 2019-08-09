package org.QGStudio.service.impl;

import org.QGStudio.dao.LocationDao;
import org.QGStudio.model.Location;
import org.QGStudio.model.LocationWithHeight;
import org.QGStudio.service.ThermoDiagramService;
import org.QGStudio.util.GeoHashUtil;
import org.QGStudio.util.TableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName ThermoDiagramServiceImpl
 * @Description TODO
 * @Author huange7
 * @Date 2019-08-09 9:46
 * @Version 1.0
 */
@Service
public class ThermoDiagramServiceImpl implements ThermoDiagramService {
    @Autowired
    private LocationDao locationDao;

    @Override
    public List findHeatMap(Location location) {
        String[] strings = GeoHashUtil.findNeighborGeohash(location);
        List<LocationWithHeight> locations = new LinkedList<>();

        for (String s :
                strings) {
            System.out.println(s);
            Date startDate = new Date(location.getTime());
            Date endDate = new Date(location.getTime());
            endDate.setSeconds(endDate.getSeconds()+5);
            DateFormat format = new SimpleDateFormat("yy-mm-dd hh:mm:ss");
            locations.addAll(locationDao.findLocation(TableUtil.getTable(new Date(location.getTime())),format.format(startDate),format.format(endDate),s.substring(s.lastIndexOf(" ")+1)+"%"));
        }
        Map<String ,Integer> map = new HashMap<>();
        for (LocationWithHeight locationWithHeight: locations) {
            if(!map.containsKey(locationWithHeight.getGeohash())){
                map.put(locationWithHeight.getGeohash(),1);
            }else{
                map.put(locationWithHeight.getGeohash(),map.get(locationWithHeight.getGeohash())+1);
            }
        }
        List points = new LinkedList();
        Set<String> keys = map.keySet();
        for (String k:
                keys) {
            LocationWithHeight locationWithHeight =GeoHashUtil.geohash2Location(k);
            locationWithHeight.setCount(map.get(k));
            points.add(locationWithHeight);
            System.out.println(locationWithHeight.toString());
        }
        return points;
    }
}
