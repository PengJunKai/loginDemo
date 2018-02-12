package com.peng.controller;

import com.peng.config.ConfigBean;
import com.peng.service.UserService;
import com.peng.utils.StrKit;
import com.peng.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by PengJK on 2018/1/8.
 */
@RestController
@RequestMapping(value = "user")
@Api(value = "用户")
public class UserController {

    @Autowired
    private ConfigBean configBean;
    @Autowired
    private UserService userService;

    @ApiOperation(value = "测试", notes = "测试")
    @GetMapping("/aaa")
    public String hexo() {
        return configBean.getName()+","+configBean.getWant()+"热部署测试";
    }

    @ApiOperation(value = "新增")
    @PostMapping("/add")
    public String add(@RequestBody UserVO userVO) {
        if(StrKit.isBlank(userVO.getUserName()) || StrKit.isBlank(userVO.getPassword())) {
            return "账号密码不能为空";
        }
        if(!isEmail(userVO.getRegisterEmail())) {
            return "邮箱地址有误";
        }
        return userService.add(userVO);
    }

    @ApiOperation(value = "验证")
    @PostMapping("/validate")
    public String validate(UserVO userVO) {
        if(StrKit.isBlank(userVO.getUserName()) || StrKit.isBlank(userVO.getPassword())) {
            return "账号密码不能为空";
        }
        return userService.validate(userVO);
    }

    private Boolean isEmail(String email) {
        if(StrKit.isBlank(email)) {
            return false;
        }
        String RULE_EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        //正则表达式的模式
        Pattern p = Pattern.compile(RULE_EMAIL);
        //正则表达式的匹配器
        Matcher m = p.matcher(email);
        if(!m.matches()) {
            return false;
        }
        return true;
    }
}
