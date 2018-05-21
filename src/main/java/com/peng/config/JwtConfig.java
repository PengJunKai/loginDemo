package com.peng.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * jwt相关配置
 *  Created by Rukiy on 2017-11-21
 */
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

    private boolean enabled = false;

    //http请求头所需要的字段
    private String header = "Authorization";

    //jwt秘钥
    private String secret = "yao";
    //单位天
    private Long expiration = 86400L;

    //md5加密混淆key
    private String md5Key = "yk";

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    public String getMd5Key() {
        return md5Key;
    }

    public void setMd5Key(String md5Key) {
        this.md5Key = md5Key;
    }
}
