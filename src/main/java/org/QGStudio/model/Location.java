package org.QGStudio.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @ClassName Location
 * @Description
 * @Author huange7
 * @Date 2019-08-08 21:07
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class Location {

    // 经度
    private Double longitude;
    // 纬度
    private Double latitude;
    // 时间
    private String time;

}
