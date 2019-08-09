package org.QGStudio.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

@Service
public interface RoutePlanService {

    /**
     * 查询单条路线
     * @param time
     * @param routeId
     * @return
     */
    String findSingleRoute(String time, int routeId) throws JsonProcessingException;
}
