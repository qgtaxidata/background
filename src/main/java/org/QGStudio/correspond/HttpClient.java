package org.QGStudio.correspond;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.QGStudio.exception.CheckException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description : 与数据挖掘交互客户端
 * @Param :
 * @Return :
 * @Author : SheldonPeng
 * @Date : 2019-08-08
 */
@Component
@Scope("prototype")
@Log4j2
public class HttpClient {


    // json工具类
    private ObjectMapper objectMapper = new ObjectMapper();
    // 创建HttpClient客户端
    private CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    /**
     * @Description : 发送带参数的post方法
     * @Param : [t]
     * @Return : java.util.List<?>
     * @Author : SheldonPeng
     * @Date : 2019-08-08
     */
    public String doPostWithParam(Object object , String url) throws JsonProcessingException {

        // 建立post请求
        HttpPost httpPost = new HttpPost(url);
        // 转换成json对象
        StringEntity entity = new StringEntity(objectMapper.writeValueAsString(object), "UTF-8");
        log.info("请求内容: "+entity.toString());
        // 加入post请求中
        httpPost.setEntity(entity);
        // 设置请求头
        httpPost.setHeader("Content-Type", "application/json;charset=utf8");
        // 响应模型
        CloseableHttpResponse response = null;

        String responseContent = "";
        try {
            response = httpClient.execute(httpPost);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();

            log.info("响应状态为:{}",response.getStatusLine());
            if (responseEntity != null) {

                log.info("响应内容长度为:{}",responseEntity.getContentLength());
                responseContent = EntityUtils.toString(responseEntity);
                log.info("响应内容为:{}" , responseContent);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new CheckException("网络通信异常，请重试");

        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return responseContent;

    }



}
