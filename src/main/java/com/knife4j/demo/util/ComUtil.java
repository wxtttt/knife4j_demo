package com.knife4j.demo.util;

import com.knife4j.demo.model.Result;

/**
 * @author wangxutao
 * @date 2021/7/1 20:52
 */
public class ComUtil {

    //返回对象
    public static <T> Result<T> getResult(T data) {
        Result<T> result = new Result<>();
        result.setData(data);
        return result;
    }
}
