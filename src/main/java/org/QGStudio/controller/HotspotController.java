package org.QGStudio.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.QGStudio.dtos.ResultBean;
import org.QGStudio.exception.CheckException;
import org.QGStudio.model.Location;
import org.QGStudio.service.HotspotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

/**
 * @description 热点推荐
 * @author < a href=" ">郭沛</ a>
 * @date 2019-08-08 19:28
 */
@RestController(value = "/hotspot")
@RequestMapping(value = "/hotspot")
public class HotspotController {

    @Autowired
    private HotspotService hotspotService;

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public ResultBean<?> findHotspot(@RequestBody Location location) throws JsonProcessingException {


        return new ResultBean<>(hotspotService.findHotspot(location));

    }
}
