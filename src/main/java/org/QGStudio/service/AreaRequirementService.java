package org.QGStudio.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface AreaRequirementService {


    /**
     * @Description : 区域出租出需求量分析
     * @Param : [area, time]
     * @Return : java.lang.Object
     * @Author : SheldonPeng
     * @Date : 2019-08-14
     */
    Object analyRequirement(int area,String time);
}
