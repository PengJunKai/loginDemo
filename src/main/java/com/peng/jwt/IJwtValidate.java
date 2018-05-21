package com.peng.jwt;

import com.peng.utils.exception.AppException;

/**
 * Created by Rukiy on 2017-12-25
 */
public interface IJwtValidate {

    /**
     * 验证顺序
     * @return
     */
    Integer sort();

    /**
     * 验证
     * @param requestHeader
     * @return
     * @throws AppException
     */
    boolean validate( String requestHeader ) throws AppException;

}
