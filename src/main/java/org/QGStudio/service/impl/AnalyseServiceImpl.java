package org.QGStudio.service.impl;

import org.QGStudio.dao.AnalyseDao;
import org.QGStudio.model.Location;
import org.QGStudio.model.Taxi;
import org.QGStudio.service.AnalyseService;
import org.QGStudio.util.GCJ02_WGS84;
import org.QGStudio.util.TableUtil;
import org.QGStudio.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @ClassName AnalyseServiceImpl
 * @Description TODO
 * @Author huange7
 * @Date 2019-08-10 16:25
 * @Version 1.0
 */
@Service
public class AnalyseServiceImpl implements AnalyseService {

    @Autowired
    private AnalyseDao analyseDao;

    @Override
    public List analyseTaxi(double maxLon, double minLon, double maxLat, double minLat, String time) {


        Location locationMax = new Location().setLatitude(maxLat).setLongitude(maxLon);
        Location locationMin = new Location().setLatitude(minLat).setLongitude(minLon);

        locationMax = GCJ02_WGS84.gcj02_To_Wgs84(locationMax.getLatitude(), locationMax.getLongitude());
        locationMin = GCJ02_WGS84.gcj02_To_Wgs84(locationMin.getLatitude(), locationMin.getLongitude());

        Date startTime = TimeUtil.StrToDate(time);
        Date endTime = startTime;
        // 增加15秒
        endTime.setSeconds(endTime.getSeconds() + 15);

        String table = TableUtil.getGpsdataTable(startTime);

        // 进行该地区该时间内的出租车查询
        List<Taxi> taxis = analyseDao.findPlateno(table, locationMax.getLongitude(), locationMin.getLongitude(), locationMax.getLatitude(),
                locationMin.getLatitude(), startTime, endTime);

        // 获取当前时间对应的新的订单表
        table = TableUtil.getOperateHisTable(startTime);

        // 用于计算载客数量
        int counts = 0;

        for (Taxi taxi:
             taxis) {
            taxi = analyseDao.findTaxi(table, taxi.getPlateno());
            Date workBeginTime = TimeUtil.StrToDate(taxi.getWorkBeginTime());
            if ( workBeginTime.after(startTime) && workBeginTime.before(endTime) ){
                counts ++;
            }
        }
        System.out.println("载客频率为："+ counts*1.0/taxis.size());

        return null;
    }
}
