package com.peng.constant;

/**
 * Created by PengJK on 2018/3/21.
 */
public enum  rightsType {

    Master("Master","站长"),

    Developer("Developer","开发者"),

    SuperManager("SuperManager","超级管理员"),

    Manager("Manager","管理员"),

    User("User","普通用户");

    private String code;
    private String name;

    rightsType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
