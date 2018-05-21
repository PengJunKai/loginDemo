package com.peng.jwt;

import com.peng.utils.CollectionKit;
import com.peng.utils.SpringContextHolder;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * IJwtValidate生产工厂
 * Created by Rukiy on 2017-12-25
 */
public class ValidateFactory {

    private static List<IJwtValidate> validates;

    /**
     * 获取所有的验证器
     * @return
     */
    public static List<IJwtValidate> getValidates() {
        if (CollectionKit.isEmpty(validates)) {
            //初始化 验证器
            validates = CollectionKit.newArrayList();
            Map<String, IJwtValidate> validateMap = SpringContextHolder.getApplicationContext().getBeansOfType(IJwtValidate.class);
            validates.addAll(validateMap.values());
            //去掉排序直为空的
            validates = validates.stream ().filter(validate -> validate.sort()!=null).collect(Collectors.toList());
            //排序 数字越小 越在前面
            validates = validates.stream().sorted(Comparator.comparingInt(IJwtValidate::sort)).collect(Collectors.toList());
        }
        return validates;
    }


}
