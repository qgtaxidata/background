package org.QGStudio.controller.aop;

import org.QGStudio.dtos.ResultBean;
import org.QGStudio.exception.CheckException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.text.ParseException;

/**
 * @Description : 异常统一处理器
 * @Param :
 * @Return :
 * @Author : SheldonPeng
 * @Date : 2019-08-05
 */
@Aspect
@Component
public class ControllerAop {


    @Around("execution(public org.QGStudio.dtos.ResultBean org.QGStudio.controller..*(..))")
    public Object handlerControllerMethod(ProceedingJoinPoint pjp) {
        long startTime = System.currentTimeMillis();

        ResultBean<?> result;

        try {
            result = (ResultBean<?>) pjp.proceed();

        } catch (Throwable e) {

            result = handlerException(pjp, e);
        }

        return result;
    }

    private ResultBean<?> handlerException(ProceedingJoinPoint pjp, Throwable e) {
        ResultBean<?> result = new ResultBean();

        // 已知异常
        if (e instanceof CheckException) {
            result.setMsg(e.getLocalizedMessage());
            result.setCode(ResultBean.FAIL);
        } else if (e instanceof NullPointerException) {

            result.setMsg("错误!参数不匹配");
            result.setCode(ResultBean.FAIL);
        } else if (e instanceof ParseException) {

            result.setMsg("请输入正确的时间!");
            result.setCode(ResultBean.FAIL);
        } else {
            // 未知异常
            e.printStackTrace();
            result.setMsg("未知异常,请稍后重试");
            result.setCode(ResultBean.FAIL);
        }
        return result;
    }

}
