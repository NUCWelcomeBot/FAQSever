/*
 *   #-*- coding = utf-8 -*-
 *   #@Time: 2021/9/12 16:08
 *   #@Author：dodo
 *   #@Software：IntelliJ IDEA
 */

package com.faq.javacustomerserver.utils;

public enum Code {
    //全局状态码
    SUCCESS(200,"操作成功！"),
    NOT_FOUND(404,"目标不存在！"),
    FAIL(-1,"操作失败！"),
    EXCEPTION(504,"服务端异常!");
    //操作代码
    int code;
    //提示信息
    String message;
    /**
     * 构造方法
     * @param code
     * @param message
     */
    Code(int code, String message) {
        this.code = code;
        this.message = message;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
