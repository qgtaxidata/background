package org.QGStudio.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import org.QGStudio.dtos.ResultBean;
import org.QGStudio.exception.CheckException;
import org.QGStudio.model.Location;
import org.QGStudio.service.HotspotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

/**
 * @author < a href=v " ">郭沛</ a>
 * @description 热点推荐
 * @date 2019-08-08 19:28
 */
@RestController(value = "/hotspot")
@RequestMapping(value = "/hotspot")
@CrossOrigin
@Log4j2
public class HotspotController {

    @Autowired
    private HotspotService hotspotService;

    @RequestMapping(value = "/findHotspot")
    public ResultBean<?> findHotspot(@RequestBody Location location , HttpServletResponse httpServletResponse) throws JsonProcessingException {

        try {
            log.info(location);
            return new ResultBean<>(hotspotService.findHotspot(location));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResultBean<>(new CheckException("未知错误,请重试!"));
    }
}
