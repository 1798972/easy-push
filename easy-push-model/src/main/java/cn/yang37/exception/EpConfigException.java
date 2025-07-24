package cn.yang37.exception;

import cn.yang37.enums.ErrorCodeEnum;

/**
 * @description:
 * @class: ConfigException
 * @author: yang37z@qq.com
 * @date: 2024/5/20 20:27
 * @version: 1.0
 */
public class EpConfigException extends EasyPushException {
    public EpConfigException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getMsg());
        this.code = errorCodeEnum.getCode();
        this.msg = errorCodeEnum.getMsg();
    }
}