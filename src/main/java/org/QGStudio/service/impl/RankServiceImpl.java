package org.QGStudio.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
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
        String response = clientBean.getObject().doPostWithParam(new Object[]{area,date});

        if (VerifyUtil.isEmpty(response)) {
            throw new CheckException("网络通讯异常！请重试！");
        }

        return response;
    }
}
