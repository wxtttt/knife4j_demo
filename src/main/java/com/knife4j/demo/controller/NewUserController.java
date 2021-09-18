package com.knife4j.demo.controller;

import com.knife4j.demo.config.ApiVersion;
import com.knife4j.demo.config.ApiVersionCst;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangxutao
 * @desc 测试版本号注解
 * @createTime 2021/9/18 13:52
 * @since 1.0.1
 */
@Api(tags = "新版用户模块")
@ApiVersion(ApiVersionCst.VERSION_2_8)
@RestController
@RequestMapping("new")
public class NewUserController {

    @GetMapping("sayHi")
    public String sayHi() {return "Hi";}
}
