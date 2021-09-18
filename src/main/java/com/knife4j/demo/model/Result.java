package com.knife4j.demo.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxutao
 * @date 2021/7/1 20:34
 */

@Data
public class Result<T> implements Serializable {

    @ApiModelProperty(value = "请求成功标示")
    private boolean success = true;

    @ApiModelProperty(value = "默认操作成功；如果操作失败，需要写清楚具体错误信息")
    private String message = "操作成功";

    @ApiModelProperty(value = "200：请求成功；500：请求失败", required = true)
    private String code = "200";

    @ApiModelProperty(value = "业务数据", required = true)
    private T data;
}
