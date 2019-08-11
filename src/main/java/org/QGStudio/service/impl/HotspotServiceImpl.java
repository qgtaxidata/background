package org.QGStudio.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.QGStudio.correspond.HttpClient;
import org.QGStudio.exception.CheckException;
import org.QGStudio.model.Location;
import org.QGStudio.service.HotspotService;
import org.QGStudio.util.DigitUtil;
import org.QGStudio.util.TimeUtil;
import org.QGStudio.util.VerifyUtil;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @description 热点推荐
 * @author < a href=" ">郭沛</ a>
 * @date 2019-08-08 19:32
 */
@Service
@Log4j2
public class HotspotServiceImpl implements HotspotService {


    @Autowired
    private ObjectFactory<HttpClient> clientBean;

    // json工具类
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 获取热点推荐的List集合
     * @param location time
     * @return
     */
    @Override
    public Object findHotspot(Location location) throws IOException {

        //location中的部分字段为空
        if (VerifyUtil.locationIsEmpty(location)) {
            throw new CheckException("location的部分字段不能为空！");
        }
        //将传进来的经纬度设置为6位小数
        location = DigitUtil.checkLocationDigit(location);
        //对time进行切割到分钟，去除秒的部分
        try {
            location.setTime(TimeUtil.formatTime(location.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
            throw new CheckException("参数异常，请检查!");
        }
        String reponse = clientBean.getObject().doPostWithParam(location);

        if (VerifyUtil.isEmpty(reponse)) {
            throw new CheckException("网络通讯异常！请重试！");
        }

        return  objectMapper.readValue(reponse,List.class);

    }
}
