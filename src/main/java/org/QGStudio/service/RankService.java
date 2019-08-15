package org.QGStudio.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

@Service
public interface RankService {

    Object getRank(int area, String date) throws JsonProcessingException;

    Object getSituation(int area,String date,String driverID) throws JsonProcessingException;

}
