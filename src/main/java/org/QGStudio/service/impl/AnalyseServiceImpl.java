package org.QGStudio.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.QGStudio.correspond.HttpClient;
import org.QGStudio.correspond.HttpUrl;
import org.QGStudio.dao.AnalyseDao;
import org.QGStudio.exception.CheckException;
import org.QGStudio.model.Location;
import org.QGStudio.model.Taxi;
import org.QGStudio.service.AnalyseService;
import org.QGStudio.util.GCJ02_WGS84;
import org.QGStudio.util.TableUtil;
import org.QGStudio.util.TimeUtil;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName AnalyseServiceImpl
 * @Description TODO
 * @Author huange7
 * @Date 2019-08-10 16:25
 * @Version 1.0
 */
@Service
@Log4j2
public class AnalyseServiceImpl implements AnalyseService {

    @Autowired
    private AnalyseDao analyseDao;

    @Autowired
    private ObjectFactory<HttpClient> clientBean;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List analyseTaxi(double maxLon, double minLon, double maxLat, double minLat, String time) {


        Location locationMax = new Location().setLatitude(maxLat).setLongitude(maxLon);
        Location locationMin = new Location().setLatitude(minLat).setLongitude(minLon);

        GCJ02_WGS84.gcj02_To_Wgs84(locationMax);
        GCJ02_WGS84.gcj02_To_Wgs84(locationMin);

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

    @Override
    public Object analyseBillboard(int area, int targetTime, int targetDay) {
        if (area == 0){
            log.info("用户选择全广州");
            throw new CheckException("暂不支持全广州推荐广告牌");
        }
        Map<String , Object> map = new HashMap<>();
        map.put("area", area);
        map.put("targetTime", targetTime);
        map.put("targetDay",targetDay);
        log.info("用户进行查询广告牌推荐，输入的数据为：{}",map);
        String response = null;
        try {
            response = clientBean.getObject().doPostWithParam(map, HttpUrl.URL+"/taxi/api/v1.0/GetBillboard");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new CheckException("网络通信异常，请稍后重试!");
        }
        log.info("收到树蛙回复，内容长度为：{}", response.length());
        Object object = null;
        try {
            object = objectMapper.readValue(response,Object.class);
            log.info(object);
        } catch (IOException e) {
            e.printStackTrace();
            throw new CheckException("网络通信异常，请稍后重试!");
        }
        return object;
    }

    @Override
    public Object analyseIncome(int area, String date) {
        Map<String ,Object> map = new HashMap<>();
        map.put("area",area);
        map.put("date", date);
        log.info("用户进行收入分析，输入数据为{}",map);
        String response = null;
        try {
            response = clientBean.getObject().doPostWithParam(map, HttpUrl.URL+"/taxi/api/v1.0/GetIncomePrediction");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new CheckException("网络通信异常，请稍后重试!");
        }
        log.info("收到树蛙回复，内容长度为：{}", response.length());
        Object object = null;
        try {
            object = objectMapper.readValue(response,Object.class);
            log.info(object);
        } catch (IOException e) {
            e.printStackTrace();
            throw new CheckException("网络通信异常，请稍后重试!");
        }
        return object;
    }

    @Override
    public Object getVehicleUtilizationRate(int area, String date) {
        Map<String ,Object> map = new HashMap<>();
        map.put("area",area);
        map.put("date", date);
        log.info("用户进行收入分析，输入数据为{}",map);
        String response = null;
        try {
            response = clientBean.getObject().doPostWithParam(map, HttpUrl.URL+"/taxi/api/v1.0/GetTaxiUtilization");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new CheckException("网络通信异常，请稍后重试!");
        }
        log.info("收到树蛙回复，内容长度为：{}", response.length());
        Object object = null;
        try {
            object = objectMapper.readValue(response,Object.class);
            log.info(object);
        } catch (IOException e) {
            e.printStackTrace();
            throw new CheckException("网络通信异常，请稍后重试!");
        }
        return object;
    }
}
