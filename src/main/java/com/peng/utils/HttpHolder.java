package com.peng.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Rukiy on 2017-12-01
 */
public class HttpHolder {

    private static final ThreadLocal<HttpServletRequest> requestThreadLocal = new ThreadLocal<>();

    private static final ThreadLocal<String> requestIdThreadLocal = new ThreadLocal<>();


    public static void put(HttpServletRequest request) {
        requestThreadLocal.set(request);
        requestIdThreadLocal.set(String.valueOf(System.currentTimeMillis()));
    }

    public static HttpServletRequest get() {
        return requestThreadLocal.get();
    }

    public static String getRequestId() {
        return requestIdThreadLocal.get();
    }

    /**
     * 获取请求ip
     */
    public static String getRequestIp() {
        return IpKit.getIpAddr(get());
    }


    public static void remove() {
        requestThreadLocal.remove();
    }
}
