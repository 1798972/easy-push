package cn.yang37.easypush.entity.enums;

import cn.yang37.easypush.entity.exception.EasyPushException;
import cn.yang37.easypush.entity.exception.EpConvertException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Constructor;

/**
 * @description:
 * @class: ErrorCode
 * @author: yang37z@qq.com
 * @date: 2025/7/24 23:30
 * @version: 1.0
 */
@Getter
@AllArgsConstructor
public enum ErrorCodeEnum {

    UNKNOWN(-1, "未知异常"),
    SUCCESS(1000, "成功"),

    /**
     * ============================================= 4xxx参数错误 ====================================================
     */
    CONFIG_NULL(4000, "配置文件解析失败"),
    SCENE_NULL(4001, "消息场景MessageSceneType不能为空"),
    MESSAGE_NULL(4002, "消息对象Message不能为空"),
    SERVICE_NULL(4003, "服务对象AbstractMessageService不能为空"),

    /**
     * ============================================= 5xxx运行错误 ====================================================
     */
    CONVERT_ERROR(5001, "类型转换异常"),
    CHAIN_EXECUTE_ERROR(5002, "职责链运行异常"),

    ;


    /**
     * 错误码
     */
    private final int code;

    /**
     * 信息
     */
    private final String msg;

    public EasyPushException toException() {
        return new EasyPushException(this);
    }

    public <T extends EasyPushException> T toException(Class<T> clazz) {
        try {
            Constructor<T> c = clazz.getConstructor(ErrorCodeEnum.class);
            return c.newInstance(this);
        } catch (Exception e) {
            throw new EpConvertException(this);
        }
    }

}