package com.peng.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by PengJK on 2018/1/8.
 */
@Configuration
@ConfigurationProperties(prefix = "com.peng")
public class ConfigBean {
    private String name;
    private String want;
    private String fromMailAddr;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWant() {
        return want;
    }

    public void setWant(String want) {
        this.want = want;
    }

    public String getFromMailAddr() {
        return fromMailAddr;
    }

    public void setFromMailAddr(String fromMailAddr) {
        this.fromMailAddr = fromMailAddr;
    }
}
