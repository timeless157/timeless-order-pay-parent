package com.timeless.exception;

import com.timeless.constants.AppHttpCodeEnum;

/**
 * @Author 三更  B站： https://space.bilibili.com/663528522
 */
public class SystemException extends RuntimeException{

    private final int code;

    private final String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public SystemException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }
    
}
