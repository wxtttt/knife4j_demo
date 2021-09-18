package com.knife4j.demo.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.knife4j.demo.model.Result;
import com.knife4j.demo.util.ComUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * @author wangxutao
 * @date 2021/6/30 20:40
 */
@Api(tags = "首页模块")
@ApiSupport(author = "wangxutao@cnstrong.com",order = 283)
@RestController
@Validated
@RequestMapping("index")
public class IndexController {


    @ApiOperation(value = "向客人问好")
    @ApiImplicitParam(name = "name", value = "姓名", paramType = "query", required = true)
    @GetMapping("/sayHi")
    public String sayHi(@NotNull String name) {
        return "Hi:" + name;
    }

    @ApiOperation(value = "输出当前host")
    @GetMapping("/getHost")
    public Result<String> getHost() {
        String hostAddress = "";
        try {
            InetAddress addr = InetAddress.getLocalHost();
            hostAddress = addr.getHostAddress();
            System.out.println("IP地址：" + addr.getHostAddress() + "，主机名：" + addr.getHostName());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ComUtil.getResult(hostAddress);
    }
}
