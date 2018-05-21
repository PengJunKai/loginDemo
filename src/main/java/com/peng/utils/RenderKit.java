package com.peng.utils;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 渲染工具类
 *  Created by Rukiy on 2017-11-21
 */
public class RenderKit {

    private RenderKit() {
        // 静态类不可实例化
    }


    /**
     * 渲染json对象
     */
    public static void renderJson(HttpServletResponse response, Object object) {
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            writer.write(JSON.toJSONString(object));
        } catch (IOException e) {
            e.getStackTrace();
        }
    }
}
