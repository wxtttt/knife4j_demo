package com.knife4j.demo.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author wangxutao
 * @date 2021/7/2 14:02
 */
@Data
public class UserTypeReq {
    @NotNull
//    @Range(min = 1, max = 3, message = "用户类型 1-3")
    @Max(3)
    @Min(1)
    @ApiModelProperty(value = "用户类型: 1-学生, 2-家长, 3-老师", required = true)
    private Integer type;
}
