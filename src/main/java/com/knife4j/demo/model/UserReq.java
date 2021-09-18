package com.knife4j.demo.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author wangxutao
 * @date 2021/7/1 11:28
 */
@Data
public class UserReq {

    @ApiModelProperty(value = "学校id", hidden = true)
    private Long schoolId;

    @NotNull
    @ApiModelProperty(value = "年级id", required = true)
    private Long gradeId;

    @NotNull
    @ApiModelProperty(value = "班级id", required = true)
    private Long clazzId;


    @ApiModelProperty(value = "性别")
    private Boolean sex;

}
