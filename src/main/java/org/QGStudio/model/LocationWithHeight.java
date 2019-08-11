package org.QGStudio.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName LocationWithHeight
 * @Description
 * @Author huange7
 * @Date 2019-08-08 22:13
 * @Version 1.0
 */
@Data
public class LocationWithHeight extends Location implements Serializable {

    // 左边对应的geohash
    String geohash;
    // 权重
    int count;
}
