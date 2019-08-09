package org.QGStudio.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import org.QGStudio.correspond.HttpClient;
import org.QGStudio.exception.CheckException;
import org.QGStudio.model.Location;
import org.QGStudio.service.RoutePlanService;
import org.QGStudio.util.DigitUtil;
import org.QGStudio.util.VerifyUtil;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName RoutePlanServiceImpl
 * @Description TODO
 * @Author huange7
 * @Date 2019-08-09 15:55
 * @Version 1.0
 */
@Service
@Log4j2
public class RoutePlanServiceImpl implements RoutePlanService {

    @Autowired
    private ObjectFactory<HttpClient> clientBean;

    /**
     * @title : 查询所有的路径
     * @param :[startLng, startLat, endLng, endLat]
     * @return : java.lang.String
     * @author : huange7
     * @date : 2019-08-09 16:07
     */
    @Override
    public String findAllRoutes(double startLng, double startLat, double endLng, double endLat) throws JsonProcessingException {

        Location start = new Location();
        Location end = new Location();
        // 设置起点的坐标和终点的坐标
        start.setLongitude(startLng).setLatitude(startLat);
        end.setLongitude(endLng).setLatitude(endLat);

        log.info("用户请求查询所有路径，起点为：[经度]：{}, [纬度]：{}",startLng,startLat);
        log.info("用户请求查询所有路径，终点为：[经度]：{}, [纬度]：{}",endLng,endLat);
        //location中的部分字段为空
        if (VerifyUtil.locationIsEmpty(start) || VerifyUtil.locationIsEmpty(end)) {
            throw new CheckException("location的部分字段不能为空！");
        }
        //将传进来的经纬度设置为6位小数
        start = DigitUtil.checkLocationDigit(start);
        end = DigitUtil.checkLocationDigit(end);

        String response = clientBean.getObject().doPostWithParam(new Object[]{start,end});

        if (VerifyUtil.isEmpty(response)) {
            throw new CheckException("网络通讯异常！请重试！");
        }
        log.info("获取到数据挖掘组的响应,长度为：{}",response.length());

        return response;

    }
}
