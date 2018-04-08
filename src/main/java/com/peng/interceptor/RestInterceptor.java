package com.peng.interceptor;

import com.peng.filter.RestFilter;
import com.peng.utils.DateKit;
import com.peng.utils.HttpHolder;
import com.peng.utils.JsonKit;
import com.peng.utils.exception.ExceptionKit;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Rest调用的性能监控
 * 需要过滤器包装
 *
 * @see RestFilter
 * Created by Rukiy on 2017-11-21
 */
public class RestInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ThreadLocal<Long> startTimeThreadLocal = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (logger.isInfoEnabled()) {
            startTimeThreadLocal.set(System.currentTimeMillis());
        }
        //所有调用 都带上记录下ip信息
        HttpHolder.put(request);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (!(request instanceof ContentCachingRequestWrapper)) {
            return;
        }
        if (!logger.isInfoEnabled()) {
            return;
        }
        //结束时间
        long endTime = System.currentTimeMillis();
        long startTime = startTimeThreadLocal.get();

        startTimeThreadLocal.remove();

        Map<String, String> logContent = new HashMap<>();

        //留着出参先不管
        Enumeration<String> enumeration = request.getParameterNames();
        Map<String, String> input = new HashMap<>();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            input.put(key, request.getParameter(key));
        }

        /*这个代码是用来记录Rest的HTTP BODY的*/
        ContentCachingRequestWrapper wrapper = (ContentCachingRequestWrapper) request;
        byte[] buf = wrapper.getContentAsByteArray();
        if (buf.length > 0) {
            int length = buf.length;
            String payload;
            try {
                payload = new String(buf, 0, length, wrapper.getCharacterEncoding());
            } catch (UnsupportedEncodingException e) {
                payload = "[unknown]";
            }
            logContent.put("body", payload);
        }
        String req;
        try {
            req = JsonKit.beanToJson(input);
        } catch (Exception e) {
            req = "入参无法解析";
        }
        logContent.put("request", req);

        logContent.put("uri", request.getRequestURI());


        logContent.put("usedTime", buildMillisecond(endTime - startTime) + "秒");

        logContent.put("state", String.valueOf(response.getStatus()));
        logContent.put("method", request.getMethod());

        boolean isError = false;

        Throwable throwable;
        if (ex != null) {
            throwable = ex;
        } else {
            throwable = (Throwable) request.getAttribute("org.springframework.web.servlet.DispatcherServlet.EXCEPTION");
        }
        if (throwable != null) {
            isError = true;
            logContent.put("error", throwable.getMessage());
            logContent.put("stackTrace", ExceptionKit.toString(throwable));
        }

        logContent.put("RequestTime", DateKit.getTime(startTime));
        logContent.put("ResponseTime", DateKit.getTime(endTime));

        logContent.put("Client", HttpHolder.getRequestIp());
        logContent.put("ApiName", getUrlInfo(handler));
        logContent.put("RequestId", HttpHolder.getRequestId());


        /*这个地方故意的使用isDebugEnabled来判断,然后使用info来记录*/
        if (isError) {
            logger.error(JsonKit.beanToJson(logContent));
        } else {
            logger.info(JsonKit.beanToJson(logContent));
        }
    }

    /**
     * 获取注解信息
     *
     * @param handler
     * @return
     */
    private String getUrlInfo(Object handler) {
        if (handler == null || !(handler instanceof HandlerMethod)) {
            //不是这个Spring的处理,没法处理
            return "UnKnow";
        }
        /*获取到Rest调用的方法上的注解.这里已经在它的方法中做了缓存了,所以性能不是问题*/
        ApiOperation apiOperation = ((HandlerMethod) handler).getMethodAnnotation(ApiOperation.class);
        if (apiOperation == null) {
            return "UnKnow";
        }
        return apiOperation.value();
    }

    private String buildMillisecond(long _millisecond) {
        String second = String.valueOf(_millisecond / 1000);
        String millisecond = String.valueOf(_millisecond % 1000);
        int n = 3 - millisecond.length();
        for (int i = 0; i < n; i++) {
            millisecond = "0" + millisecond;
        }
        return second + "." + millisecond;
    }

}
