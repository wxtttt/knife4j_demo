package com.knife4j.demo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author wangxutao
 * @date 2021/7/1 10:33
 */
@Data
@ApiModel(value = "User", description = "用户信息")
public class User {

    @NotNull
    @ApiModelProperty(value = "用户id", example = "1111", required = true)
    private Long id;

    @NotNull
    @ApiModelProperty(value = "用户姓名", example = "小明", required = true)
    private String name;


    public User(@NotNull Long id, @NotNull String name) {
        this.id = id;
        this.name = name;
    }
}
