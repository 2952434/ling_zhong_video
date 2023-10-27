package com.lingzhong.video.bean.vo;

/**
 * @Author: Ljx
 * @Date: 2023/10/27 20:33
 * @role: 给前端回复数据实体
 */
public class RespBean {
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
    private Object obj;

    public static RespBean build() {
        return new RespBean();
    }

    public static RespBean ok(Object obj) {
        return new RespBean(200, null, obj);
    }

    public static RespBean ok(String msg) {
        return new RespBean(200, msg, null);
    }

    public static RespBean ok(String msg, Object obj) {
        return new RespBean(200, msg, obj);
    }

    public static RespBean error(Object obj) {
        return new RespBean(500, null, obj);
    }

    public static RespBean error(String msg) {
        return new RespBean(500, msg, null);
    }

    public static RespBean error(String msg, Object obj) {
        return new RespBean(500, msg, obj);
    }

    private RespBean() {
    }

    private RespBean(Integer status, String msg, Object obj) {
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

    public Object getObj() {
        return obj;
    }

    public RespBean setObj(Object obj) {
        this.obj = obj;
        return this;
    }
}
