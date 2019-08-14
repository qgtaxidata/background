package org.QGStudio.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

@Service
public interface RankService {

    String getRank(int area, String date) throws JsonProcessingException;

    String getSituation(int area,String date,String driverID) throws JsonProcessingException;

}
