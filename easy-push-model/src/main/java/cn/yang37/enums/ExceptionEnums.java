package cn.yang37.enums;

/**
 * @description:
 * @class: ExeceptionEnums
 * @author: yang37z@qq.com
 * @date: 2023/4/15 10:52
 * @version: 1.0
 */
public enum ExceptionEnums {

    /**
     *
     */
    HTTP_ERROR(1, "HTTP请求异常");

    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    ExceptionEnums(int code, String msg) {
    }
}