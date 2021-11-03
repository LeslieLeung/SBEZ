package com.ameow.sbez.constants;

/**
 * @author Leslie Leung
 * @description 返回结果
 * @date 2021/11/3
 */
public enum ResultCode {
    SUCCESS(0, "操作成功"),
    FAILED(400, "操作失败"),
    SERVER_ERROR(500, "服务器错误"),
    VALIDATE_FAILED(404, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限");

    private int code;
    private String msg;

    private ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
