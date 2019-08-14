package org.QGStudio.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.QGStudio.correspond.HttpClient;
import org.QGStudio.exception.CheckException;
import org.QGStudio.service.RankService;
import org.QGStudio.util.VerifyUtil;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public String getRank(int area, String date) throws JsonProcessingException {

        if (VerifyUtil.isEmpty(area)) {
            throw new CheckException("地区的字段不能为空");
        }
        if (VerifyUtil.isEmpty(date)) {
            throw new CheckException("时间字段不能为空");
        }
        String response = clientBean.getObject().doPostWithParam(new Object[]{area,date},"http://192.168.1.101:8080/taxi/api/v1.0/IncomeRank");

        if (VerifyUtil.isEmpty(response)) {
            throw new CheckException("网络通讯异常！请重试！");
        }

        return response;
    }

    @Override
    public String getSituation(int area, String date, String driverID)throws  JsonProcessingException {
        log.info("前端请求信息："+"area = "+area+" date = "+date+" driverID = "+driverID);

        if (VerifyUtil.isEmpty(area)) {
            throw new CheckException("地区的字段不能为空");
        }
        if (VerifyUtil.isEmpty(date)) {
            throw new CheckException("时间字段不能为空");
        }
        if (VerifyUtil.isEmpty(driverID)) {
            throw new CheckException("司机id字段不能为空");
        }
        String response = clientBean.getObject().doPostWithParam(new Object[]{area,date,driverID},"http://192.168.1.101:8080/taxi/api/v1.0/GetDriverInfo");

        log.info("树蛙响应信息："+response);

        if (VerifyUtil.isEmpty(response)) {
            throw new CheckException("网络通讯异常！请重试！");
        }

        return response;
    }
}
