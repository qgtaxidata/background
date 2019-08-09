package org.QGStudio.runnable;

import ch.hsr.geohash.BoundingBox;
import ch.hsr.geohash.GeoHash;
import org.QGStudio.dao.LocationDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author <a href="mailto:kobe524348@gmail.com">黄钰朝</a>
 * @description
 * @date 2019-08-09 15:33
 */
public class FindGeohashRUnnable implements Runnable {

    /**
     * 操作的表名
     */
    private String table;
    /**
     * 起始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;

    private BoundingBox boundingBox;
    /**
     * 容器
     */
    private List list;
    private LocationDao locationDao;
    private CountDownLatch countDownLatch;

    public FindGeohashRUnnable(String table, Date startTime,
                               Date endTime, BoundingBox boundingBox, List list,
                               LocationDao locationDao, CountDownLatch countDownLatch) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");

        this.table = table;
        this.startTime = startTime;
        this.endTime = endTime;
        this.boundingBox = boundingBox;
        this.list = list;
        this.locationDao = ac.getBean(LocationDao.class);
        this.countDownLatch = countDownLatch;
    }

    /**
     * 查询一个5级geohash块中所有符合条件的gps记录
     *
     * @return
     * @name run
     * @notice none
     * @author <a href="mailto:kobe524348@gmail.com">黄钰朝</a>
     * @date 2019-08-09
     */
    @Override
    public void run() {
        list.addAll(locationDao.findLocation(table,boundingBox.getMaxLon(),boundingBox.getMinLon(),
                boundingBox.getMaxLat(),boundingBox.getMinLat(),startTime,endTime));
        countDownLatch.countDown();
    }
}
