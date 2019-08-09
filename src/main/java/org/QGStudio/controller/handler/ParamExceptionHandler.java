package org.QGStudio.controller.handler;

import org.QGStudio.dtos.ResultBean;
import org.QGStudio.exception.CheckException;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @Description : 参数异常捕获
 * @Param :
 * @Return :
 * @Author : SheldonPeng
 * @Date : 2019-08-05
 */

@ControllerAdvice
@ResponseBody
public class ParamExceptionHandler {


    /**
     * @Description : 400 (Bad Request)
     * @Param : [exception]
     * @Return : org.qgstudio.www.dtos.ResultBean<?>
     * @Author : SheldonPeng
     * @Date : 2019-08-08
     */
    @ExceptionHandler(value = TypeMismatchException.class)
    public ResultBean<?> requestTypeMismatch(TypeMismatchException exception){

        throw new CheckException("错误!参数不匹配");
    }

    /**
     * @Description : 400 (Bad Request)
     * @Param : [exception]
     * @Return : org.qgstudio.www.dtos.ResultBean<?>
     * @Author : SheldonPeng
     * @Date : 2019-08-08
     */
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResultBean<?> missParamException(MissingServletRequestParameterException exception){

        throw new CheckException("错误!参数不匹配");
    }

    /**
     * @Description : 400 (Bad Request)
     * @Param : [exception]
     * @Return : org.qgstudio.www.dtos.ResultBean<?>
     * @Author : SheldonPeng
     * @Date : 2019-08-08
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResultBean<?> nullPointException(HttpMessageNotReadableException exception){

        throw new CheckException("错误!参数不匹配");
    }


    /**
     * @Description :  500 (Internal Server Error)
     * @Param : [exception]
     * @Return : org.qgstudio.www.dtos.ResultBean<?>
     * @Author : SheldonPeng
     * @Date : 2019-08-08
     */
    @ExceptionHandler(value = HttpMessageNotWritableException.class)
    public ResultBean<?> paramException(HttpMessageNotWritableException exception){

        throw new CheckException("错误!参数不匹配");
    }

    /**
     * @Description : 500 (Internal Server Error) 
     * @Param : [exception]
     * @Return : org.qgstudio.www.dtos.ResultBean<?>
     * @Author : SheldonPeng
     * @Date : 2019-08-08
     */
    @ExceptionHandler(value = ConversionNotSupportedException.class)
    public ResultBean<?> paramException(ConversionNotSupportedException exception){

        throw new CheckException("错误!参数不匹配");
    }
}
