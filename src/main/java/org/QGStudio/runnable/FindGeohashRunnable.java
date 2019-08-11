package org.QGStudio.runnable;

import ch.hsr.geohash.BoundingBox;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
public class FindGeohashRunnable implements Runnable {

    /**
     * 操作的表名
     */
    protected String table;
    /**
     * 起始时间
     */
    protected Date startTime;
    /**
     * 结束时间
     */
    protected Date endTime;

    protected BoundingBox boundingBox;
    /**
     * 容器
     */
    protected List list;
    protected LocationDao locationDao;
    protected CountDownLatch countDownLatch;

    public FindGeohashRunnable(String table, Date startTime,
                               Date endTime, BoundingBox boundingBox, List list, CountDownLatch countDownLatch) {
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
        log.info(new Date().toLocaleString()+"开始SQL查询");
                list.addAll(locationDao.findLocation(table, boundingBox.getMaxLon(), boundingBox.getMinLon(),
                boundingBox.getMaxLat(), boundingBox.getMinLat(), startTime, endTime));
        countDownLatch.countDown();
        log.info(new Date().toLocaleString()+"结束SQL查询");

    }
}
