package com.peng.service;

import com.peng.mapper.UserMapper;
import com.peng.model.User;
import com.peng.utils.DateKit;
import com.peng.vo.UserVO;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

/**
 * Created by PengJK on 2018/1/18.
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public String add(UserVO userVO) {
        User user = new User();
        user.setUserName(userVO.getUserName());
        Date creteDate = new Date();
        user.setCreateDate(creteDate);
        String salt = String.valueOf(creteDate.getTime());
        user.setSalt(salt);

        StringBuilder password = new StringBuilder(userVO.getPassword()).append(salt);

        String sha256 = getSHA256StrJava(password.toString());

        user.setPassword(sha256);

        try {
            userMapper.insert(user);
            return "注册成功";
        } catch (Exception e) {
            logger.debug(e.toString());
            return "error";
        }

    }

    public String validate(UserVO userVO) {

        User user = new User();
        user.setUserName(userVO.getUserName());
        user = userMapper.selectOne(user);
        if(user == null) {
            return "用户不存在";
        } else {
            StringBuilder password = new StringBuilder(userVO.getPassword()).append(user.getSalt());

            String sha256 = getSHA256StrJava(password.toString());

            if(sha256.equals(user.getPassword())) {
                return "验证成功";
            } else {
                return "用户名或密码错误";
            }
        }
    }

    /**
     *  利用java原生的摘要实现SHA256加密
     * @param str 加密后的报文
     * @return
     */
    private static String getSHA256StrJava(String str){
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }

    /**
     * 将byte转为16进制
     * @param bytes
     * @return
     */
    private static String byte2Hex(byte[] bytes){
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i=0;i<bytes.length;i++){
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length()==1){
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }


}
