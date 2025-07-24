package cn.yang37.easypush.entity.exception;

import cn.yang37.easypush.entity.enums.ErrorCodeEnum;

/**
 * @description:
 * @class: ExecuteException
 * @author: yang37z@qq.com
 * @date: 2024/5/20 22:57
 * @version: 1.0
 */
public class EpChainExecuteException extends EasyPushException {

    public EpChainExecuteException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getMsg());
        this.code = errorCodeEnum.getCode();
        this.msg = errorCodeEnum.getMsg();
    }

}