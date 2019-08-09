package org.QGStudio.controller;


        import com.fasterxml.jackson.core.JsonProcessingException;
        import org.QGStudio.dtos.ResultBean;
        import org.QGStudio.model.Location;
        import org.QGStudio.service.RoutePlanService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.web.bind.annotation.*;

        import java.util.List;

/**
 * @ClassName RoutePlanController
 * @Description TODO
 * @Author huange7
 * @Date 2019-08-09 15:23
 * @Version 1.0
 */
@RestController
@RequestMapping("/route")
@CrossOrigin
public class RoutePlanController {

    @Autowired
    RoutePlanService routePlanService;

    @PostMapping("/findAll")
    public ResultBean<?> findAllRoute(@RequestBody double startLng,@RequestBody double startLat,@RequestBody double endLng,@RequestBody double endLat) throws JsonProcessingException {

        return new ResultBean<>(routePlanService.findAllRoutes(startLng,startLat,endLng,endLat));
    }
}
