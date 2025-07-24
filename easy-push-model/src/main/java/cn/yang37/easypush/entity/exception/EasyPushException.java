package cn.yang37.easypush.entity.exception;

import cn.yang37.easypush.entity.enums.ErrorCodeEnum;
import lombok.Getter;

/**
 * @description:
 * @class: PushCoreException
 * @author: yang37z@qq.com
 * @date: 2023/4/15 10:51
 * @version: 1.0
 */
@Getter
public class EasyPushException extends RuntimeException {

    protected int code;

    protected String msg;

    public EasyPushException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getMsg());
        this.code = errorCodeEnum.getCode();
        this.msg = errorCodeEnum.getMsg();
    }

    public EasyPushException(ErrorCodeEnum errorCodeEnum, Throwable cause) {
        super(errorCodeEnum.getMsg(), cause);
        this.code = errorCodeEnum.getCode();
        this.msg = errorCodeEnum.getMsg();
    }

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