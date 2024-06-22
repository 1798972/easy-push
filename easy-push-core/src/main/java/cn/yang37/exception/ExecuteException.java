package cn.yang37.exception;

/**
 * @description:
 * @class: ExecuteException
 * @author: yang37z@qq.com
 * @date: 2024/5/20 22:57
 * @version: 1.0
 */
public class ExecuteException extends EasyPushException {
    public ExecuteException() {
        super();
    }

    public ExecuteException(String message) {
        super(message);
    }

    public ExecuteException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExecuteException(Throwable cause) {
        super(cause);
    }

    protected ExecuteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}