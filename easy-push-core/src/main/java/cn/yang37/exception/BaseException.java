package cn.yang37.exception;

import cn.yang37.enums.ExceptionEnums;

/**
 * @description:
 * @class: PushCoreException
 * @author: yang37z@qq.com
 * @date: 2023/4/15 10:51
 * @version: 1.0
 */
public class BaseException extends RuntimeException {

    public BaseException() {
        super();
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    protected BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public BaseException(ExceptionEnums exceptionEnums) {
        super(exceptionEnums.getCode() + ":" + exceptionEnums.getMsg());
    }

    public BaseException(ExceptionEnums exceptionEnums, Throwable cause) {
        super(exceptionEnums.getCode() + ":" + exceptionEnums.getMsg(), cause);
    }

}