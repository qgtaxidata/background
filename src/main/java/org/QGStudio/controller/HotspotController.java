package org.QGStudio.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import org.QGStudio.dtos.ResultBean;
import org.QGStudio.model.Location;
import org.QGStudio.service.HotspotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @description 热点推荐
 * @author < a href=" ">郭沛</ a>
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

        log.info(location);
        return new ResultBean<>(hotspotService.findHotspot(location));

    }
}
