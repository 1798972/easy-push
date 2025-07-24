package cn.yang37.exception.npe;

import cn.yang37.enums.ErrorCodeEnum;
import cn.yang37.exception.EasyPushException;

/**
 * @description:
 * @class: EpServiceNullException
 * @author: yang37z@qq.com
 * @date: 2025/7/24 23:46
 * @version: 1.0
 */
public class EpServiceNullException extends EasyPushException {
    public EpServiceNullException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getMsg());
        this.code = errorCodeEnum.getCode();
        this.msg = errorCodeEnum.getMsg();
    }
}