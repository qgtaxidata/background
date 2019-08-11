package org.QGStudio.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @ClassName Point
 * @Description
 * @Author huange7
 * @Date 2019-08-08 22:13
 * @Version 1.0
 */
@Data
@Accessors(chain = true)
public class Point implements Serializable {
    Double lng;
    Double lat;
    int count;
}
