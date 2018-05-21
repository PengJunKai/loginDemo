package com.peng.jwt.impl;

import com.peng.jwt.IJwtValidate;
import com.peng.jwt.JwtTokenComponent;
import com.peng.utils.StrKit;
import com.peng.utils.exception.AppException;
import com.peng.utils.exception.ExceptionType;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 默认的jwt验证
 * Created by Rukiy on 2017-12-25
 */
@Component
public class DefaultJwtValidate implements IJwtValidate {

    @Autowired
    JwtTokenComponent jwtTokenComponent;

    /**
     * 验证顺序
     *
     * @return
     */
    @Override
    public Integer sort() {
        //默认的验证器必须排在最后一个
        return 99999;
    }

    /**
     * 验证
     *
     * @param requestHeader
     * @return
     * @throws AppException
     */
    @Override
    public boolean validate(String requestHeader) throws AppException{
        if ( StrKit.isNotEmpty(requestHeader)) {
            try {
                //验证token是否过期,包含了验证jwt是否正确
                boolean isExpired = jwtTokenComponent.isTokenExpired(requestHeader);
                if (isExpired) {
                    throw new AppException( ExceptionType.TOKEN_EXPIRED, ExceptionType.TOKEN_EXPIRED.getDefaultMsg());
                }
            } catch (JwtException e) {
                //有异常就是token解析失败
                throw new AppException(ExceptionType.TOKEN_ERROR, ExceptionType.TOKEN_ERROR.getDefaultMsg());
            }
        } else {
            //header为空
            throw new AppException(ExceptionType.TOKEN_EMPTY, ExceptionType.TOKEN_EMPTY.getDefaultMsg());
        }
        return true;
    }
}
