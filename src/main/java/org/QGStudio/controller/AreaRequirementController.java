package org.QGStudio.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.QGStudio.dtos.ResultBean;
import org.QGStudio.service.AreaRequirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: $
 * @Param: $
 * @return: $
 * @author: SheledonPeng
 * @Date: $
 */
@RestController
@RequestMapping("/AreaRequirement")
@CrossOrigin
public class AreaRequirementController {

    @Autowired
    private AreaRequirementService areaRequirementService;

    @GetMapping("/analyseRequirement")
    public ResultBean<?> analyRequirement(@RequestParam("time") String time , @RequestParam("area") int area) {

        return new ResultBean<>(areaRequirementService.analyRequirement(area,time));
    }
}
