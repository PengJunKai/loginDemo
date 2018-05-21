package com.peng.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by PengJK on 2018/5/21.
 */
@RestController
@RequestMapping(value = "manage")
@Api(value = "权限管理模块")
public class ManageController {

    @ApiOperation(value = "测试")
    @PostMapping
    public String add() {
        return "测试";
    }
}
