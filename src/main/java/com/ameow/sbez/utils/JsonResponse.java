package com.ameow.sbez.utils;

import com.ameow.sbez.constants.ResultCode;

/**
 * @author Leslie Leung
 * @description 返回结果封装
 * @date 2021/11/3
 */
public class JsonResponse<T> {
    private int code;
    private String msg;
    private T data;

    protected JsonResponse() {
    }

    protected JsonResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    protected JsonResponse(T data) {
        this.code = 0;
        this.msg = "操作成功";
        this.data = data;
    }

    protected JsonResponse(int code, String msg, T data) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public static <T> JsonResponse<T> success() {
        return new JsonResponse<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg());
    }


    public static <T> JsonResponse<T> success(T data) {
        return new JsonResponse<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), data);
    }

    public static <T> JsonResponse<T> success(T data, String msg) {
        return new JsonResponse<T>(ResultCode.SUCCESS.getCode(), msg, data);
    }

    public static JsonResponse<Object> error() {
        return new JsonResponse<>(ResultCode.FAILED.getCode(), ResultCode.FAILED.getMsg());
    }

    public static JsonResponse<Object> error(String msg) {
        return new JsonResponse<>(ResultCode.FAILED.getCode(), msg);
    }

    /**
     * 未登录返回结果
     */
    public static <T> JsonResponse<T> unauthorized(T data) {
        return new JsonResponse<T>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMsg(), data);
    }

    /**
     * 未授权返回结果
     */
    public static <T> JsonResponse<T> forbidden(T data) {
        return new JsonResponse<T>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMsg(), data);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
