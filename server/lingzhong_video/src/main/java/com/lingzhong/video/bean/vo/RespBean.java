package com.lingzhong.video.bean.vo;

/**
 * @Author: Ljx
 * @Date: 2023/10/27 20:33
 * @role: 给前端回复数据实体
 */
public class RespBean<T> {
    /**
     * 状态码
     */
    private Integer status;
    /**
     * 响应内容
     */
    private String msg;
    /**
     * 响应数据
     */
    private T obj;

    public static <T> RespBean<T> build() {
        return new RespBean<T>();
    }

    public static <T> RespBean<T> ok(T obj) {
        return new RespBean<T>(200, null, obj);
    }

    public static <T> RespBean<T> ok(String msg) {
        return new RespBean<T>(200, msg, null);
    }

    public static <T> RespBean<T> ok(String msg, T obj) {
        return new RespBean<T>(200, msg, obj);
    }

    public static <T> RespBean<T> error(T obj) {
        return new RespBean<T>(500, null, obj);
    }

    public static <T> RespBean<T> error(String msg) {
        return new RespBean<T>(500, msg, null);
    }

    public static <T> RespBean<T> error(String msg, T obj) {
        return new RespBean<T>(500, msg, obj);
    }

    private RespBean() {
    }

    private RespBean(Integer status, String msg, T obj) {
        this.status = status;
        this.msg = msg;
        this.obj = obj;
    }

    public Integer getStatus() {
        return status;
    }

    public RespBean setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public RespBean setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getObj() {
        return obj;
    }

    public RespBean setObj(T obj) {
        this.obj = obj;
        return this;
    }
}
