package com.peng.jwt.impl;

import com.peng.jwt.IJwtValidate;
import com.peng.jwt.JwtTokenComponent;
import com.peng.utils.StrKit;
import com.peng.utils.exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 超级token验证
 * Created by Rukiy on 2017-12-25
 */
@Component
public class SuperTokenJwtValidate implements IJwtValidate {

    @Autowired
    JwtTokenComponent jwtTokenComponent;

    /**
     * 验证顺序
     *
     * @return
     */
    @Override
    public Integer sort() {
        return 0;
    }

    /**
     * 验证
     * @param requestHeader
     * @return
     * @throws AppException
     */
    @Override
    public boolean validate(String requestHeader) throws AppException{
        if ( StrKit.isNotEmpty(requestHeader)) {
            //超级token
            if (requestHeader.equalsIgnoreCase("yao")){
                return true;
            }
        }
        return false;
    }
}
