package cn.yang37.exception;

/**
 * @description:
 * @class: PushCoreException
 * @author: yang37z@qq.com
 * @date: 2023/4/15 10:51
 * @version: 1.0
 */
public class EasyPushException extends RuntimeException {

    public EasyPushException() {
        super();
    }

    public EasyPushException(String message) {
        super(message);
    }

    public EasyPushException(String message, Throwable cause) {
        super(message, cause);
    }

    public EasyPushException(Throwable cause) {
        super(cause);
    }

    protected EasyPushException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}