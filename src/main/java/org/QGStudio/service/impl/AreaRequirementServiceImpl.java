package org.QGStudio.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.QGStudio.correspond.HttpClient;
import org.QGStudio.exception.CheckException;
import org.QGStudio.service.AreaRequirementService;
import org.QGStudio.util.VerifyUtil;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: $
 * @Param: $
 * @return: $
 * @author: SheledonPeng
 * @Date: $
 */
@Service
@Log4j2
public class AreaRequirementServiceImpl implements AreaRequirementService {


    @Autowired
    private ObjectFactory<HttpClient> clientBean;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Object analyRequirement(int area, String time) {

        if (VerifyUtil.isEmpty(time)){
            throw new CheckException("请选择正确的时间!");
        }
        Map<String,Object> map = new HashMap<>();
        map.put("area",area);
        map.put("time",time);

        String response = null;
        try {
            response = clientBean.getObject().doPostWithParam(map,"http://192.168.31.89:8080//taxi/api/v1.0/Demand");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new CheckException("网络通信异常，请稍后重试!");
        }
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
