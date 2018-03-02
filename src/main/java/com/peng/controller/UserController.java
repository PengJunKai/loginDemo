package com.peng.controller;

import com.peng.service.UserService;
import com.peng.utils.StrKit;
import com.peng.utils.tips.R;
import com.peng.utils.tips.Tip;
import com.peng.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    public Tip<UserVO> add(@RequestBody UserVO userVO) {
        if(StrKit.isBlank(userVO.getUserName()) || StrKit.isBlank(userVO.getPassword())) {
            return R.error("账号密码不能为空");
        }
        userVO = userService.add(userVO);
        return R.success(userVO,"注册成功");

    }

    @ApiOperation(value = "验证")
    @PostMapping("/validate")
    public Tip validate(@RequestBody UserVO userVO) {
        if(StrKit.isBlank(userVO.getUserName()) || StrKit.isBlank(userVO.getPassword())) {
            return R.error("账号密码不能为空");
        }
        String message = userService.validate(userVO);
        if("200".equals(message)) {
            return R.success();
        } else {
            return R.error(message);
        }
    }

    @ApiOperation(value = "发送修改密码链接")
    @PostMapping("/getSecretKey")
    public Tip getSecretKey(HttpServletRequest request, UserVO userVO) {
        if(StrKit.isBlank(userVO.getUserName())) {
            return R.error("账号不能为空");
        }
        String message = userService.getSecretKey(request,userVO);
        if("200".equals(message)) {
            return R.success();
        } else {
            return R.error(message);
        }
    }

    @ApiOperation(value = "修改密码")
    @PostMapping("/resetPassword")
    public Tip resetPassword(@RequestBody UserVO userVO) {
        if(StrKit.isBlank(userVO.getUserName()) || StrKit.isBlank(userVO.getPassword())) {
            return R.error("账号密码不能为空");
        }
        if(StrKit.isBlank(userVO.getSecretKey())) {
            return R.error("密匙不能为空");
        }
        String message = userService.resetPassword(userVO);
        if("200".equals(message)) {
            return R.success();
        } else {
            return R.error(message);
        }
    }
}
