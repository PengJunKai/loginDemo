package com.peng.utils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Rukiy on 2017-12-01
 */
public class IpKit {

    /**
     * 获取真实IP地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return "";
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");

        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip != null && ip.length() > 0) {
            String[] ips = ip.split(",");
            for (int i = 0; i < ips.length; i++) {
                if (ips[i].trim().length() > 0
                        && !"unknown".equalsIgnoreCase(ips[i].trim())) {
                    ip = ips[i].trim();
                    break;
                }
            }
        }
        return ip;
    }

    public static String getLocalHost() {
        InetAddress ia = null;
        try {
            ia = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ia.getHostAddress();
    }
}
