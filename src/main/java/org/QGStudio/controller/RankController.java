package org.QGStudio.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import org.QGStudio.dtos.ResultBean;
import org.QGStudio.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description 排行榜
 * @author < a href=" ">郭沛</ a>
 * @date 2019-08-13 17:11
 */
@RestController(value = "/rank")
@RequestMapping(value = "/rank")
@CrossOrigin
@Log4j2
public class RankController {

    @Autowired
    private RankService rankService;

    @RequestMapping("/getRank")
    public ResultBean<?> getRank(@RequestParam int area, @RequestParam String date) throws JsonProcessingException {
        return new ResultBean<>(rankService.getRank(area, date));
    }

    @RequestMapping("/getSituation")
    public ResultBean<?> getSituation(@RequestParam int area, @RequestParam String date,@RequestParam("driverID") String driverID) throws JsonProcessingException {
        return new ResultBean<>(rankService.getSituation(area, date,driverID));
    }

}
