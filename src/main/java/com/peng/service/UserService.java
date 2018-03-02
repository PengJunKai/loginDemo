package com.peng.service;

import com.peng.mapper.UserMapper;
import com.peng.model.User;
import com.peng.utils.StrKit;
import com.peng.vo.UserVO;
import org.apache.commons.net.smtp.SMTPClient;
import org.apache.commons.net.smtp.SMTPReply;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.Record;
import org.xbill.DNS.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by PengJK on 2018/1/18.
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MailService mailService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public String add(UserVO userVO) {
        User user = new User();
        user.setUserName(userVO.getUserName());
        Date createDate = new Date();
        user.setCreateDate(createDate);
        String salt = String.valueOf(createDate.getTime());
        user.setSalt(salt);

        StringBuilder password = new StringBuilder(userVO.getPassword()).append(salt);

        String sha256 = getSHA256StrJava(password.toString());

        user.setPassword(sha256);

        if(isEmail(userVO.getRegisterEmail())) {
            user.setRegisterEmail(userVO.getRegisterEmail());
        } else {
            return "邮箱错误";
        }

        try {
            userMapper.insert(user);
            return "200";
        } catch (Exception e) {
            logger.debug(e.toString());
            return "注册失败";
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
                return "200";
            } else {
                return "用户名或密码错误";
            }
        }
    }

    public String getSecretKey(HttpServletRequest request, UserVO userVO) {
        User user = new User();
        user.setUserName(userVO.getUserName());
        user = userMapper.selectOne(user);
        if(user == null) {
            return "未查询到此用户";
        }

        String secretKey = UUID.randomUUID().toString().replaceAll("-",""); // 密钥
        Long invalidDate = System.currentTimeMillis() + 30 * 60 * 1000;// 30分钟后过期
        user.setSecretKey(secretKey);
        user.setInvalidDate(invalidDate);
        userMapper.updateById(user);

        String basePath = new StringBuffer(request.getScheme()).append("://").append(request.getServerName()).append(":")
                .append(request.getServerPort()).append(request.getContextPath()).append("/").toString();

        String resetPassHref = new StringBuffer(basePath).append("checkLink?sid=").append(user.getSecretKey()).append("&userName=")
                .append(user.getUserName()).toString();

        String emailContent = new StringBuffer("请勿回复本邮件.点击下面的链接,重设密码<br/><a href=")
                .append(resetPassHref).append(" target='_BLANK'>").append(resetPassHref)
                .append("</a>  或者    <a href=").append(resetPassHref)
                .append(" target='_BLANK'>点击我重新设置密码</a>")
                .append("<br/>tips:本邮件超过30分钟,链接将会失效，需要重新申请'找回密码'")
                .append("\t").append(resetPassHref).toString();

        if(mailService.sendHtmlMail(user.getRegisterEmail(),"修改密码",emailContent)) {
            return "200";
        }

        return "邮件发送失败";

    }

    public String resetPassword(UserVO userVO) {
        User user = new User();
        user.setUserName(userVO.getUserName());
        user = userMapper.selectOne(user);
        if(user == null) {
            return "未查询到此用户";
        }
        if(!user.getSecretKey().equals(userVO.getSecretKey())) {
            return "密匙不匹配，请重新获取正确的链接";
        }
        if(user.getInvalidDate()<System.currentTimeMillis()) {
            return "链接已超时，请重新获取";
        }

        StringBuilder password = new StringBuilder(userVO.getPassword()).append(user.getSalt());

        String sha256 = getSHA256StrJava(password.toString());

        user.setPassword(sha256);

        try {
            userMapper.updateById(user);
            return "200";
        } catch (Exception e) {
            logger.debug(e.toString());
            return "修改失败";
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

    /**
     * 验证邮箱
     * @param email
     * @return
     */
    private boolean isEmail(String email) {
        if(StrKit.isBlank(email)) {
            return false;
        }
        String RULE_EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        //正则表达式的模式
        Pattern p = Pattern.compile(RULE_EMAIL);
        //正则表达式的匹配器
        Matcher m = p.matcher(email);
        if(!m.matches()) {
            return false;
        }
        String host = "";
        String hostName = email.split("@")[1];

        Record[] result = null;
        SMTPClient client = new SMTPClient();
        try{
            Lookup lookup = new Lookup(hostName, Type.MX);
            lookup.run();
            if(lookup.getResult() != Lookup.SUCCESSFUL) {
                logger.error("邮箱("+email+")验证未通过,未查找到对应的MX记录");
            } else {
                result = lookup.getAnswers();
            }
            for(int i = 0;i<result.length;i++) {
                host = result[i].getAdditionalName().toString();
                logger.debug("SMTPClient try connect to host:"+host);

                client.connect(host);

                if(!SMTPReply.isPositiveCompletion(client.getReplyCode())) {
                    client.disconnect();
                    continue;
                } else {
                    logger.debug("找到MX记录:"+hostName);
                    logger.debug("建立链接成功："+hostName);
                    break;
                }
            }
            logger.debug("SMTPClient ReplyString:"+client.getReplyString());
            String emailSuffix="163.com";
            String emailPrefix="ranguisheng";
            String fromEmail = emailPrefix+"@"+emailSuffix;
            //Login to the SMTP server by sending the HELO command with the given hostname as an argument.
            //Before performing any mail commands, you must first login.
            //尝试和SMTP服务器建立连接,发送一条消息给SMTP服务器
            client.login(emailPrefix);
            logger.debug("SMTPClient login:"+emailPrefix+"...");
            logger.debug("SMTPClient ReplyString:"+client.getReplyString());

            //Set the sender of a message using the SMTP MAIL command,
            //specifying a reverse relay path.
            //The sender must be set first before any recipients may be specified,
            //otherwise the mail server will reject your commands.
            //设置发送者，在设置接受者之前必须要先设置发送者
            client.setSender(fromEmail);
            logger.debug("设置发送者 :"+fromEmail);
            logger.debug("SMTPClient ReplyString:"+client.getReplyString());

            //Add a recipient for a message using the SMTP RCPT command,
            //specifying a forward relay path. The sender must be set first before any recipients may be specified,
            //otherwise the mail server will reject your commands.
            //设置接收者,在设置接受者必须先设置发送者，否则SMTP服务器会拒绝你的命令
            client.addRecipient(email);
            logger.debug("设置接收者:"+email);
            logger.debug("SMTPClient ReplyString:"+client.getReplyString());
            logger.debug("SMTPClient ReplyCode："+client.getReplyCode()+"(250表示正常)");
            if (250 == client.getReplyCode()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                client.disconnect();
            } catch (IOException e) {

            }
        }
        return false;
    }
}
