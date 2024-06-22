package cn.yang37.exception;

/**
 * @description:
 * @class: ConfigException
 * @author: yang37z@qq.com
 * @date: 2024/5/20 20:27
 * @version: 1.0
 */
public class ConfigException extends EasyPushException {
    public ConfigException() {
        super();
    }

    public ConfigException(String message) {
        super(message);
    }

    public ConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigException(Throwable cause) {
        super(cause);
    }

    protected ConfigException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}