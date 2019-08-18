package org.QGStudio.correspond;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @ClassName HttpUrl
 * @Description TODO
 * @Author huange7
 * @Date 2019-08-17 15:04
 * @Version 1.0
 */
@Log4j2
@Component
@Scope("singleton")
public class HttpUrl {

    public static String PORT = "";

    public static String URL = "";

    public static String DOMAIN = "";

    public static void setUrl(String domain, String port){
        URL = "http://"+domain+":"+port;
    }
}
