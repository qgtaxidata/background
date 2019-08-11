package org.QGStudio.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @ClassName Taxi
 * @Description TODO
 * @Author huange7
 * @Date 2019-08-10 16:33
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Taxi {

    // 车牌
    private String plateno;

    // 运营开始时间
    private String workBeginTime;

    // 运营结束时间
    private String workEndTime;
}
