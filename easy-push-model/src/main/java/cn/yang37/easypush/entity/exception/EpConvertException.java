package cn.yang37.easypush.entity.exception;

import cn.yang37.easypush.entity.enums.ErrorCodeEnum;

/**
 * @description:
 * @class: EpExceptionConvertException
 * @author: yang37z@qq.com
 * @date: 2025/7/25 0:07
 * @version: 1.0
 */
public class EpConvertException extends EasyPushException {

    public EpConvertException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getMsg());
        this.code = errorCodeEnum.getCode();
        this.msg = errorCodeEnum.getMsg();
    }

}