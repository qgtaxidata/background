package org.QGStudio.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.QGStudio.correspond.HttpClient;
import org.QGStudio.exception.CheckException;
import org.QGStudio.service.RoutePlanService;
import org.QGStudio.util.VerifyUtil;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoutePlanServiceImpl implements RoutePlanService {

    @Autowired
    private ObjectFactory<HttpClient> clientBean;

    @Override
    public String findSingleRoute(String time, int routeId) throws JsonProcessingException {

        //时间是否为空
        if (VerifyUtil.isEmpty(time)) {
            throw new CheckException("时间参数不能为空！");
        }
        //路线Id是否为空
        if (VerifyUtil.isEmpty(routeId)) {
            throw new CheckException("路线id不能为空！");
        }
        String response = clientBean.getObject().doPostWithParam(new Object[] {time, routeId});

        if (VerifyUtil.isEmpty(response)) {
            throw new CheckException("网络通讯异常！请重试！");
        }

        return response;

    }
}
