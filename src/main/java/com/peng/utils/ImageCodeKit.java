package com.peng.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by PengJK on 2018/3/7.
 */
public class ImageCodeKit {

    private static char mapTable[] = {
            '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', '0', '1',
            '2', '3', '4', '5', '6', '7',
            '8', '9'};

    public static Map<String,Object> getImageCode(int width, int height, OutputStream os) {

        Map<String,Object> returnMap = new HashMap<String, Object>();
        if (width <= 0) width = 60;
        if (height <= 0) height = 20;

        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        // 获取图形上下文
        Graphics g = image.getGraphics();
        //随机类
        Random random = new Random();
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);

        int fontWidth = width / 4;// 字体的宽度
        int fontHeight = height - 5;// 字体的高度
        int codeY = height - 8;
        //设定字体
        g.setFont(new Font("Fixedsys", Font.BOLD, fontHeight));
        // 随机产生168条干扰线
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 168; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }
        //取随机产生的码
        String strEnsure = "";
        //4代表4位验证码,如果要生成更多位的认证码,则加大数值
        for (int i = 0; i < 4; ++i) {
            strEnsure += mapTable[(int) (mapTable.length * Math.random())];
            // 将认证码显示到图象中
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            //直接生成
            String str = strEnsure.substring(i, i + 1);
            g.drawString(str, i*fontWidth+3, codeY);
        }
        // 释放图形上下文
        g.dispose();
        returnMap.put("image",image);
        returnMap.put("strEnsure",strEnsure);
        return returnMap;
    }

    //给定范围获得随机颜色
    static Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) fc = 255;
        if (bc > 255) bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
}
