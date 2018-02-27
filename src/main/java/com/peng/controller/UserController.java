package com.peng.controller;

import com.peng.service.UserService;
import com.peng.utils.StrKit;
import com.peng.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by PengJK on 2018/1/8.
 */
@RestController
@RequestMapping(value = "user")
@Api(value = "用户")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "新增")
    @PostMapping("/add")
    public String add(@RequestBody UserVO userVO) {
        if(StrKit.isBlank(userVO.getUserName()) || StrKit.isBlank(userVO.getPassword())) {
            return "账号或密码不能为空";
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

    @ApiOperation(value = "发送修改密码链接")
    @PostMapping("/getSecretKey")
    public String getSecretKey(UserVO userVO) {
        if(StrKit.isBlank(userVO.getUserName())) {
            return "账号不能为空";
        }
        return userService.getSecretKey(userVO);
    }

    @ApiOperation(value = "修改密码")
    @PostMapping("/resetPassword")
    public String resetPassword(UserVO userVO) {
        if(StrKit.isBlank(userVO.getUserName()) || StrKit.isBlank(userVO.getPassword())) {
            return "账号或密码不能为空";
        }
        if(StrKit.isBlank(userVO.getSecretKey())) {
            return "密匙不能为空";
        }
        return userService.resetPassword(userVO);
    }
}
