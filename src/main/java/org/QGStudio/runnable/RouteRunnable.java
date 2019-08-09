package org.QGStudio.runnable;

import org.QGStudio.dao.LocationDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @Description : 路径可视化的线程执行类
 * @Param :
 * @Return :
 * @Author : SheldonPeng
 * @Date : 2019-08-09
 */
public class RouteRunnable implements Runnable{


    private LocationDao locationDao;

    private CountDownLatch countDownLatch;

    private List<?> resultList;


    public RouteRunnable( CountDownLatch countDownLatch , List<?> resultList){

        // 从Spring容器中拿到dao对象
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(LocationDao.class);
        this.locationDao = applicationContext.getBean(LocationDao.class);

        this.countDownLatch = countDownLatch;
        this.resultList = resultList;
    }


    @Override
    public void run() {


    }
}
