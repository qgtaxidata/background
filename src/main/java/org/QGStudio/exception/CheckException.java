package org.QGStudio.exception;

/**
 * @Description : 自定义显示的异常
 * @Param :
 * @Return :
 * @Author : SheldonPeng
 * @Date : 2019-08-08
 */
public class CheckException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public CheckException() {
    }

    public CheckException(String message) {
        super(message);
    }

    public CheckException(String message, Throwable cause) {
        super(message, cause);
    }

    public CheckException(Throwable cause) {
        super(cause);
    }

    protected CheckException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
