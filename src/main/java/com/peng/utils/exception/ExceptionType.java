package com.peng.utils.exception;

/**
 * 所有业务异常的枚举
 *
 *  Created by Rukiy on 2017-11-21
 */
public enum ExceptionType {

    /**
     * token异常
     */
    TOKEN_EXPIRED("Token Expired", "token过期"),
    TOKEN_ERROR("Token Error", "token验证失败"),
    TOKEN_EMPTY("Token Empty", "token未提供"),
    /**
     * 登录验证
     */
    AUTHENTICATION_ERROR("Authentication Error", "用户名或密码错误"),
    /**
     * 调用失败
     */
    REST_ERROR("Rest Error", "rest服务调用失败"),

    /**
     * 系统出错
     */
    RUNTIME_ERROR("Runtime Error", "系统异常!"),
    /**
     * 操作错误
     */
    OPERATE_ERROR("Operate Error", "操作错误！");

    ExceptionType(String code, String message) {
        this.code = code;
        this.defaultMsg = message;
    }

    private String code;

    private String defaultMsg;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDefaultMsg() {
        return defaultMsg;
    }

    public void setDefaultMsg(String defaultMsg) {
        this.defaultMsg = defaultMsg;
    }
}
