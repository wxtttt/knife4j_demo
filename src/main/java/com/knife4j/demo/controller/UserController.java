package com.knife4j.demo.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.knife4j.demo.model.Result;
import com.knife4j.demo.model.User;
import com.knife4j.demo.model.UserReq;
import com.knife4j.demo.util.ComUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

/**
 * @author wangxutao
 * @date 2021/7/1 11:48
 */
@Api(tags = "用户模块")
@ApiSupport(author = "wangxutao@cnstrong.com",order = 284)
@RestController
@Validated
@RequestMapping("user")
public class UserController {

    @ApiOperation(value = "对象")
    @GetMapping("/user123")
    public User getUser(@Valid User user) {
        return user;
    }

    @ApiOperationSupport(author = "wangxutao@163.com", order = 3)
    @ApiOperation(value = "获取用户列表")
    @GetMapping("/getUser123")
    public List<User> getUsers(@Valid UserReq userReq) {
        //获取用户列表
        return Arrays.asList(new User(100L, "小明"), new User(101L, "小红"));
    }

    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "获取用户id列表（long）")
    @GetMapping("/getUserId123")
    public List<Long> getUserId() {
        //获取用户id列表...
        return Arrays.asList(101L, 102L, 103L);
    }

    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "获取用户id列表（int）")
    @GetMapping("/getIntUserId")
    public List<Integer> getIntUserId() {
        //获取用户id列表...
        return Arrays.asList(101, 102, 103);
    }

    @ApiOperationSupport(ignoreParameters = "id")
    @ApiOperation(value = "新增用户")
    @PostMapping("/insertUser")
    public Boolean insertUser(@Valid User user) {
        //插入新用户
        return Boolean.TRUE;
    }

    @PostMapping("/deleteUser")
    @ApiOperation(value = "删除用户（result型返回结果）")
    @ApiImplicitParam(name = "userId", value = "用户id", dataType = "long", required = true, paramType = "query")
    public Result<Boolean> deleteUser(@NotNull Long userId) {
        //删除用户
        System.out.println("删除了1111");
        return ComUtil.getResult(Boolean.TRUE);
    }

    @GetMapping("/getUserInfo")
    @ApiOperation(value = "查找用户（result型返回结果）")
    @ApiImplicitParam(name = "userId", value = "用户id", dataType = "long", required = true, paramType = "query")
    public Result<User> getUserInfo(@Range(min = 1L,max = 2L) @NotNull Long userId) {
        User user = new User(100L, "小明");
        return ComUtil.getResult(user);
    }

    @ApiOperation(value = "根据类型查找用户（result型返回结果）")
    @ApiImplicitParam(name = "type", value = "用户类型: 1-学生, 2-家长, 3-老师", dataType = "int", required = true, paramType = "query")
    @GetMapping("/getUserByType")
    public Result<User> getUserByType(@Max(3) @Min(1) @NotNull Integer type) {
        User user = new User(100L, "小明");
        return ComUtil.getResult(user);
    }
}
