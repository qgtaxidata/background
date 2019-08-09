package org.QGStudio.service;

import org.QGStudio.model.Location;

import java.text.ParseException;
import java.util.List;

/**
 * @InterfaceName ThermoDiagramService
 * @Description TODO
 * @Author huange7
 * @Date 2019-08-09 8:59
 * @Version 1.0
 */
public interface ThermoDiagramService {
    List findHeatMap(Location location) throws ParseException;
}
