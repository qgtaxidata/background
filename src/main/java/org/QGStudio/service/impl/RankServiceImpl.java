package org.QGStudio.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.QGStudio.correspond.HttpClient;
import org.QGStudio.exception.CheckException;
import org.QGStudio.service.RankService;
import org.QGStudio.util.VerifyUtil;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @description
 * @author < a href=" ">郭沛</ a>
 * @date 2019-08-13 17:17
 */
@Service
@Log4j2
public class RankServiceImpl implements RankService {


    @Autowired
    private ObjectFactory<HttpClient> clientBean;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Object getRank(int area, String date) throws JsonProcessingException {

        if (VerifyUtil.isEmpty(area)) {
            throw new CheckException("地区的字段不能为空");
        }
        if (VerifyUtil.isEmpty(date)) {
            throw new CheckException("时间字段不能为空");
        }

        Map<String,Object> map = new HashMap<>();
        map.put("area",area);
        map.put("date",date);
        String response = clientBean.getObject().doPostWithParam(map,"http://192.168.31.89:8080/taxi/api/v1.0/IncomeRank");

        if (VerifyUtil.isEmpty(response)) {
            throw new CheckException("网络通讯异常！请重试！");
        }

        Object object = null;
        try {
            object =  objectMapper.readValue(response,Object.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new CheckException("网络通讯异常，请稍后重试");
        }
        return object;
    }

    @Override
    public Object getSituation(int area, String date, String driverID)throws  JsonProcessingException {
        log.info("前端请求信息："+"area = "+area+" date = "+date+" driverID = "+driverID);

        if (VerifyUtil.isEmpty(date)) {
            throw new CheckException("时间字段不能为空");
        }
        if (VerifyUtil.isEmpty(driverID)) {
            throw new CheckException("司机id字段不能为空");
        }

        Map<String,Object> map = new HashMap<>();
        map.put("area",area);
        map.put("date",date);
        map.put("driverID",driverID);

        String response = clientBean.getObject().doPostWithParam(map,
                "http://192.168.31.89:8080/taxi/api/v1.0/GetDriverInfo");

        log.info("树蛙响应信息："+response);

        if (VerifyUtil.isEmpty(response)) {
            throw new CheckException("网络通讯异常！请重试！");
        }

        Object object = null;
        try {
            object = objectMapper.readValue(response,Object.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return object;
    }
}
