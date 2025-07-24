package cn.yang37.easypush.entity.exception.npe;

import cn.yang37.easypush.entity.enums.ErrorCodeEnum;
import cn.yang37.easypush.entity.exception.EasyPushException;

/**
 * @description:
 * @class: EpMessageNullException
 * @author: yang37z@qq.com
 * @date: 2025/7/24 23:29
 * @version: 1.0
 */
public class EpMessageNullException extends EasyPushException {

    public EpMessageNullException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getMsg());
        this.code = errorCodeEnum.getCode();
        this.msg = errorCodeEnum.getMsg();
    }
}