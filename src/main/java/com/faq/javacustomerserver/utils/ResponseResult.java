/*
 *   #-*- coding = utf-8 -*-
 *   #@Time: 2021/9/12 16:01
 *   #@Author：dodo
 *   #@Software：IntelliJ IDEA
 */

package com.faq.javacustomerserver.utils;

import java.io.Serializable;

public class ResponseResult<T> implements Serializable {
    /**
     * 响应码
     */
    private Integer code;
    /**
     * 响应信息
     */
    private String message;
    /**
     * 具体数据
     */
    private T data;

    public ResponseResult(Code code, T data) {
        this.code = code.getCode();
        this.message = code.message;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
