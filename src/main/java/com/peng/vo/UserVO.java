package com.peng.vo;

import com.peng.constant.RightsType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by PengJK on 2018/1/18.
 */
@ApiModel(value = "用户")
public class UserVO {
    
    private Long uuid;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "用户密码")
    private String password;
    
    @ApiModelProperty(value = "token")
    private String userToken;

    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    @ApiModelProperty(value = "注册邮箱")
    private String registerEmail;

    @ApiModelProperty(value = "密匙")
    private String secretKey;

    @ApiModelProperty(value = "权限")
    private RightsType rights;
    
    public Long getUuid(){
        return uuid;
    }
    
    public void setUuid( Long uuid ){
        this.uuid = uuid;
    }
    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getUserToken(){
        return userToken;
    }
    
    public void setUserToken( String userToken ){
        this.userToken = userToken;
    }
    
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getRegisterEmail() {
        return registerEmail;
    }

    public void setRegisterEmail(String registerEmail) {
        this.registerEmail = registerEmail;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public RightsType getRights() {
        return rights;
    }

    public void setRights(RightsType rights) {
        this.rights = rights;
    }
}
