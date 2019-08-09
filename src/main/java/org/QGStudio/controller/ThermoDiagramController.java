package org.QGStudio.controller;

import org.QGStudio.dtos.ResultBean;
import org.QGStudio.model.Location;
import org.QGStudio.service.ThermoDiagramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

/**
 * @ClassName ThermoDiagramController
 * @Description TODO
 * @Author huange7
 * @Date 2019-08-09 10:34
 * @Version 1.0
 */
@RestController()
@RequestMapping("/thermoDiagram")
public class ThermoDiagramController {

    @Autowired
    ThermoDiagramService thermoDiagramService;

    @PostMapping("/getMap")
    public ResultBean<?> getMap(@RequestBody Location location) throws ParseException {
        return new ResultBean<>(thermoDiagramService.findHeatMap(location));
    }

}
