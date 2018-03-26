package com.peng.controller;

import com.peng.service.UserService;
import com.peng.utils.ImageCodeKit;
import com.peng.utils.StrKit;
import com.peng.utils.exception.ExceptionType;
import com.peng.utils.tips.R;
import com.peng.utils.tips.Tip;
import com.peng.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Map;

/**
 * Created by PengJK on 2018/1/8.
 */
@RestController
@RequestMapping(value = "user")
@Api(value = "用户")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "获取验证码")
    @GetMapping("/imageCode")
    public String imageCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        OutputStream os = response.getOutputStream();
        Map<String,Object> map = ImageCodeKit.getImageCode(160, 50, os);
        String simpleCaptcha = "simpleCaptcha";
        request.getSession().setAttribute(simpleCaptcha, map.get("strEnsure").toString().toLowerCase());
        request.getSession().setAttribute("codeTime",new Date().getTime());
        try {
            ImageIO.write((BufferedImage) map.get("image"), "JPEG", os);
        } catch (IOException e) {
            return null;
        }
        return null;
    }

    @ApiOperation(value = "新增")
    @PostMapping()
    public Tip add(@RequestBody UserVO userVO, String checkCode, HttpSession session) {
        if(StrKit.isBlank(userVO.getUserName()) || StrKit.isBlank(userVO.getPassword())) {
            return R.error(ExceptionType.OPERATE_ERROR.getCode(),"账号密码不能为空");
        }
        Object cko = session.getAttribute("simpleCaptcha") ; //验证码对象
        if(cko == null){
            return R.error(ExceptionType.OPERATE_ERROR.getCode(),"验证码已失效，请重新输入！");
        }
        String captcha = cko.toString();
        Date now = new Date();
        Long codeTime = Long.valueOf(session.getAttribute("codeTime")+"");
        if(StrKit.isEmpty(checkCode) || captcha == null || !(checkCode.equalsIgnoreCase(captcha))) {
            return R.error(ExceptionType.OPERATE_ERROR.getCode(),"验证码错误！");
        } else if ((now.getTime()-codeTime)/1000/60>5) {
            //验证码有效时长为5分钟
             R.error(ExceptionType.OPERATE_ERROR.getCode(),"验证码已失效，请重新输入！");
        }
        userVO = userService.add(userVO);
        //验证码使用一次，清理一次
        session.setAttribute("simpleCaptcha",null);
        return R.success(userVO,"注册成功");
    }

    @ApiOperation(value = "验证")
    @PostMapping("/validate")
    public Tip validate(@RequestBody UserVO userVO) {
        if(StrKit.isBlank(userVO.getUserName()) || StrKit.isBlank(userVO.getPassword())) {
            return R.error(ExceptionType.OPERATE_ERROR.getCode(),"账号密码不能为空");
        }
        userVO = userService.validate(userVO);
        return R.success(userVO);
    }

    @ApiOperation(value = "发送修改密码链接")
    @PostMapping("/getSecretKey")
    public Tip getSecretKey(HttpServletRequest request,@RequestBody UserVO userVO) {
        if(StrKit.isBlank(userVO.getUserName())) {
            return R.error(ExceptionType.OPERATE_ERROR.getCode(),"账号不能为空");
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
            return R.error(ExceptionType.OPERATE_ERROR.getCode(),"账号密码不能为空");
        }
        if(StrKit.isBlank(userVO.getSecretKey())) {
            return R.error(ExceptionType.OPERATE_ERROR.getCode(),"密匙不能为空");
        }
        String message = userService.resetPassword(userVO);
        if("200".equals(message)) {
            return R.success();
        } else {
            return R.error(message);
        }
    }
}
