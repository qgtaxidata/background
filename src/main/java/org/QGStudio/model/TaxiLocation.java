package org.QGStudio.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description : 出租车的实体类对象
 * @Param :
 * @Return :
 * @Author : SheldonPeng
 * @Date : 2019-08-10
 */

@Data
public class TaxiLocation extends Location implements Serializable {

    // 车牌号
    private String licenseplateno;
}
