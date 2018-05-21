package com.peng.jwt;

/**
 * Created by Rukiy on 2017-12-28
 */
public class JwtTokenHolder {

    private static final ThreadLocal<String> tokenThreadLocal = new ThreadLocal<>();

    public static void put(String token) {
        tokenThreadLocal.set(token);
    }

    public static String get() {
        return tokenThreadLocal.get();
    }
}
