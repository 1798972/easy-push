package cn.yang37.easypush.entity.exception;

import cn.yang37.easypush.entity.enums.ErrorCodeEnum;

/**
 * @description:
 * @class: EpSenceParseException
 * @author: yang37z@qq.com
 * @date: 2025/7/24 23:27
 * @version: 1.0
 */
public class EpSceneParseException extends EasyPushException{
    public EpSceneParseException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getMsg());
        this.code = errorCodeEnum.getCode();
        this.msg = errorCodeEnum.getMsg();
    }
}