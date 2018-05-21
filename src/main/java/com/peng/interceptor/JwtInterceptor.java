package com.peng.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.peng.config.JwtConfig;
import com.peng.jwt.IJwtValidate;
import com.peng.jwt.IgnoreJWT;
import com.peng.jwt.JwtTokenComponent;
import com.peng.jwt.JwtTokenHolder;
import com.peng.jwt.ValidateFactory;
import com.peng.utils.RenderKit;
import com.peng.utils.SpringContextHolder;
import com.peng.utils.exception.AppException;
import com.peng.utils.tips.ErrorTip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.ContentCachingRequestWrapper;



/**
 * jwt 验证器
 *
 * Created by Rukiy on 2017-11-21
 */
public class JwtInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(request instanceof ContentCachingRequestWrapper )) {
            return true;
        }
        //是否忽略验证
        if (ignoreValidation(handler)) {
            return true;
        }

        JwtConfig jwtConfig = getJwtTokenUtil().getJwtConfig();
        final String requestHeader = request.getHeader(jwtConfig.getHeader());
        try {
            //jwt验证
            for (IJwtValidate validate : ValidateFactory.getValidates()) {
                if (validate.validate(requestHeader)) {
                    JwtTokenHolder.put(requestHeader);
                    return true;
                }
            }
        } catch (AppException e) {
            RenderKit.renderJson(response, new ErrorTip(e.getErrCode(), e.getMessage(), e));
        }
        return false;
    }

    private JwtTokenComponent getJwtTokenUtil() {
        return SpringContextHolder.getBean(JwtTokenComponent.class);
    }

    /**
     * 是否忽略验证
     *
     * @param handler
     * @return
     */
    private boolean ignoreValidation(Object handler) {
        if (handler == null || !(handler instanceof HandlerMethod )) {
            //不是这个Spring的处理,需要验证
            return false;
        }
        /*获取到Rest调用的方法上的注解.这里已经在它的方法中做了缓存了,所以性能不是问题*/
        IgnoreJWT ignoreJWT = ((HandlerMethod) handler).getMethodAnnotation(IgnoreJWT.class);
        return ignoreJWT != null && ignoreJWT.value();
    }
}
