package org.QGStudio.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.QGStudio.model.Location;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
@Service
public interface HotspotService {

    String findHotspot(Location location) throws IOException;

}
