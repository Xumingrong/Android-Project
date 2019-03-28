package com.meteor.common.base;

import java.io.Serializable;

/**
 * Author：Meteor
 * date：2018/7/30 15:24
 * desc：基类bean
 */
public class BaseResponseBean<T> implements Serializable {
    private int code;//返回码
    private String msg;//返回信息
    private T data;//返回的业务逻辑数据

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
