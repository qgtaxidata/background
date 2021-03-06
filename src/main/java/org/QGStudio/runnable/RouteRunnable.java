package org.QGStudio.runnable;

import lombok.extern.log4j.Log4j2;
import org.QGStudio.dao.LocationDao;
import org.QGStudio.dao.RouteDao;
import org.QGStudio.model.TaxiLocation;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @Description : 路径可视化的线程执行类
 * @Param :
 * @Return :
 * @Author : SheldonPeng
 * @Date : 2019-08-09
 */
@Log4j2
public class RouteRunnable implements Runnable{


    private RouteDao routeDao;

    private CountDownLatch countDownLatch;

    // 结果集
    private List<TaxiLocation> resultList;

    // 表名
    private String table;

    // 开始时间
    private Date startTime;

    // 结束时间
    private Date endTime;

    // 车牌号
    private String licenseplateno;


    public RouteRunnable(CountDownLatch countDownLatch , List<TaxiLocation> resultList, Date startTime, Date endTime
          , String table , String licenseplateno){

        // 从Spring容器中拿到dao对象
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        this.routeDao = applicationContext.getBean(RouteDao.class);

        this.countDownLatch = countDownLatch;
        this.resultList = resultList;
        this.startTime = startTime;
        this.endTime = endTime;
        this.table = table;
        this.licenseplateno = licenseplateno;
    }


    @Override
    public void run() {

        log.info("线程中的数据为{},{},{}",table,startTime,endTime);
        long start = System.currentTimeMillis();
        log.info("开始查询数据库,现在为{}",start);
        List<TaxiLocation> list = routeDao.findTaxiLocation(table, startTime, endTime);
        long end = System.currentTimeMillis();
        resultList.addAll(list);
        list.clear();
        log.info("查询数据库完成，共用时{},大小为{}",end - start,resultList.size());

        if ( countDownLatch.getCount() == 0 ){
            resultList.clear();
            resultList = null;
            log.info("查询超时，线程中的数据开始清空");
        }
        countDownLatch.countDown();
        return;
    }
}
