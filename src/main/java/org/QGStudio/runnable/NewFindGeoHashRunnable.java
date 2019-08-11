package org.QGStudio.runnable;

import ch.hsr.geohash.BoundingBox;
import ch.hsr.geohash.GeoHash;
import lombok.extern.log4j.Log4j2;
import org.QGStudio.dao.LocationDao;
import org.QGStudio.model.Location;
import org.QGStudio.model.LocationWithHeight;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @ClassName NewFindGeoHashRunnable
 * @Description TODO
 * @Author huange7
 * @Date 2019-08-11 9:19
 * @Version 1.0
 */
@Log4j2
public class NewFindGeoHashRunnable extends FindGeohashRunnable{

    Object[] geohashs;

    LocationDao locationDao;

    public NewFindGeoHashRunnable(String table, Date startTime, Date endTime,Object[] geoHashs, List list, CountDownLatch countDownLatch) {
        super(table, startTime, endTime, null, list, countDownLatch);
        this.geohashs = geoHashs;

    }

    @Override
    public void run() {
        List<LocationWithHeight> myList = new LinkedList<>();
        for (Object g:geohashs){
            String geohash = (String) g;
            GeoHash geoHash = GeoHash.fromGeohashString(geohash);
            BoundingBox boundingBox = geoHash.getBoundingBox();
            long start = System.currentTimeMillis();
            log.info("开启时间,{}",start);
            myList.addAll(locationDao.findLocation(table, boundingBox.getMaxLon(), boundingBox.getMinLon(),
                    boundingBox.getMaxLat(), boundingBox.getMinLat(), startTime, endTime));
            long end = System.currentTimeMillis();
            log.info("共用时,{}",end-start);
        }
        list.addAll(myList);
        countDownLatch.countDown();
    }
}
