package com.peng.filter;

import com.alibaba.druid.util.ServletPathMatcher;
import com.peng.interceptor.RestInterceptor;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @see RestInterceptor
 * 包装下rest请求,便于的解析
 * Created by Rukiy on 2017-11-21
 */
public class RestFilter extends OncePerRequestFilter {

    private ServletPathMatcher matcher = new ServletPathMatcher();
    private Set<String> excludesPattern;
    private static final String PARAM_NAME_EXCLUSIONS = "exclusions";


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //是否是排除地址
        if (isExclusion(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        boolean isFirstRequest = !isAsyncDispatch(request);
        HttpServletRequest requestToUse = request;
        if (isFirstRequest && !(request instanceof ContentCachingRequestWrapper)) {
            requestToUse = new ContentCachingRequestWrapper(request);
        }
        filterChain.doFilter(requestToUse, response);
    }


    @Override
    protected void initFilterBean(){
        //初始化过滤器参数
        FilterConfig filterConfig = getFilterConfig();
        String exclusions = filterConfig.getInitParameter(PARAM_NAME_EXCLUSIONS);
        if (exclusions != null && exclusions.trim().length() != 0) {
            excludesPattern = new HashSet<>(Arrays.asList(exclusions.split("\\s*,\\s*")));
        }
    }

    private boolean isExclusion(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        if (excludesPattern == null || requestURI == null) {
            return false;
        }
        if (contextPath != null && requestURI.startsWith(contextPath)) {
            requestURI = requestURI.substring(contextPath.length());
            if (!requestURI.startsWith("/")) {
                requestURI = "/" + requestURI;
            }
        }
        for (String pattern : excludesPattern) {
            if (matcher.matches(pattern, requestURI)) {
                return true;
            }
        }
        return false;
    }
}
