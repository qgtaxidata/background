package org.QGStudio.controller;

import org.QGStudio.dao.TestDao;
import org.QGStudio.dtos.ResultBean;
import org.QGStudio.model.Location;
import org.QGStudio.model.LocationWithHeight;
import org.QGStudio.model.Point;
import org.QGStudio.model.User;
import org.QGStudio.service.TestService;
import org.QGStudio.util.GeoHashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @Description: $
 * @Param: $
 * @return: $
 * @author: SheledonPeng
 * @Date: $
 */

@RestController("/test")
@RequestMapping("/test")
@CrossOrigin
public class TestController {

    // TODO 写实体类判断类
    @Autowired
    private TestService testService;

    @Autowired
    private TestDao testDao;

    @RequestMapping("/testUser")
    public ResultBean<?> testUser(@RequestParam int userId){

        return new ResultBean<>(testService.testUser(userId));
    }

    @RequestMapping("/test")
    public ResultBean<?> testFind(@RequestBody Location location, HttpServletResponse httpServletResponse){


        return new ResultBean<>();
    }
}
