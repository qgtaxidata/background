package org.QGStudio.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description : 返回结果的字段
 * @Param : 
 * @Return : 
 * @Author : SheldonPeng
 * @Date : 2019-08-08
 */

@Data
@Builder
@AllArgsConstructor
public class ResultBean<T> implements Serializable {

    public static final int SUCCESS = 1;
    public static final int FAIL = -1;

    private String msg = "success";
    private int code = SUCCESS;

    private T data;

    public ResultBean() {
        super();
    }

    public ResultBean(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }

    public ResultBean(T data) {
        this.data = data;
    }


    public ResultBean(Throwable e) {
        super();
        this.msg = e.toString();
        this.code = FAIL;
    }
}
