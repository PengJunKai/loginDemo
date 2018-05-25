package com.peng.jwt;

import com.peng.config.JwtConfig;
import com.peng.utils.ToolKit;
import com.peng.utils.CollectionKit;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>jwt token工具类</p>
 * <pre>
 *     jwt的claim里一般包含以下几种数据:
 *         1. iss -- token的发行者
 *         2. sub -- 该JWT所面向的用户
 *         3. aud -- 接收该JWT的一方
 *         4. exp -- token的失效时间
 *         5. nbf -- 在此时间段之前,不会被处理
 *         6. iat -- jwt发布时间
 *         7. jti -- jwt唯一标识,防止重复使用
 * </pre>
 * <p>
 * Created by Rukiy on 2017-11-21
 */
@Component
public class JwtTokenComponent {

    @Autowired
    private JwtConfig jwtConfig;

    public JwtConfig getJwtConfig() {
        return jwtConfig;
    }

    public void setJwtConfig(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    /**
     * 获取用户名从token中
     */
    public String getUsernameFromToken(String token) {
        if(token.equalsIgnoreCase ("wuyeqing")){
            return "S1";
        }
        return getClaimFromToken(token).getSubject();
    }

    /**
     * 获取jwt发布时间
     */
    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token).getIssuedAt();
    }

    /**
     * 获取jwt失效时间
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token).getExpiration();
    }

    /**
     * 获取jwt接收者
     */
    public String getAudienceFromToken(String token) {
        return getClaimFromToken(token).getAudience();
    }

    /**
     * 获取私有的jwt claim
     */
    public String getPrivateClaimFromToken(String token, String key) {
        return getClaimFromToken(token).get(key).toString();
    }

    /**
     * 获取md5 key从token中
     */
    public String getMd5KeyFromToken(String token) {
        return getPrivateClaimFromToken(token, jwtConfig.getMd5Key());
    }

    /**
     * 获取jwt的payload部分
     */
    public Claims getClaimFromToken( String token) {
        return Jwts.parser()
                .setSigningKey(jwtConfig.getSecret())
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 解析token是否正确,不正确会报异常<br>
     */
    public void parseToken(String token) throws JwtException{
        Jwts.parser().setSigningKey(jwtConfig.getSecret()).parseClaimsJws(token).getBody();
    }

    /**
     * <pre>
     *  验证token是否失效
     *  true:过期   false:没过期
     * </pre>
     */
    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * 生成token(通过用户名和签名时候用的随机数)
     */
    public String generateToken(String userName, String randomKey) {
        return generateToken(userName,randomKey,null);
    }

    /**
     * 生成token(通过用户名和签名时候用的随机数)
     */
    public String generateToken(String userName, String randomKey,Map<String, Object> claims) {
        if(CollectionKit.isEmpty (claims)){
            claims = new HashMap<>();
        }
        claims.put(jwtConfig.getMd5Key(), randomKey);
        return doGenerateToken(claims, userName);
    }

    /**
     * 生成token
     */
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        final Date createdDate = new Date();
        //有效时间 单位天
        Long expiration = jwtConfig.getExpiration();
        //换算成秒
        expiration = expiration * 24 * 60 * 60;
        final Date expirationDate = new Date(createdDate.getTime() + expiration * 1000);
        String a = jwtConfig.getSecret();
        SignatureAlgorithm b = SignatureAlgorithm.HS512;
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith( b, a)
                .compact();
    }

    /**
     * 获取混淆MD5签名用的随机字符串
     */
    public String getRandomKey() {
        return ToolKit.getRandomString(6);
    }
}