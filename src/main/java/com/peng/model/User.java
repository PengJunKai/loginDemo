package com.peng.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.peng.constant.rightsType;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by PengJK on 2018/1/18.
 */
@TableName("user")
public class User<T extends Model> extends Model<T> implements Serializable{

    protected static final long serialVersionUID = 1L;

    //固定主键
    @TableId
    private Long uuid;

    @TableField(value = "username")
    private String userName;

    @TableField(value = "password")
    private String password;

    @TableField(value = "createdate")
    private Date createDate;

    @TableField(value = "salt")
    private String salt;

    @TableField(value = "registeremail")
    private String registerEmail;

    @TableField(value = "secretkey")
    private String secretKey;

    @TableField(value = "invaliddate")
    private Long invalidDate;

    @TableField(value = "rights")
    private rightsType rights;

    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
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

    public Long getInvalidDate() {
        return invalidDate;
    }

    public void setInvalidDate(Long invalidDate) {
        this.invalidDate = invalidDate;
    }

    public rightsType getRights() {
        return rights;
    }

    public void setRights(rightsType rights) {
        this.rights = rights;
    }

    @Override
    protected Serializable pkVal() {
        return this.uuid;
    }
}
