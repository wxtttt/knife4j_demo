package com.knife4j.demo.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wangxutao
 * @projectName 版本号注解
 * @desc 自己写的版本号注解，遍于swagger版本分类，可标注在类和方法上
 * @createTime 2021/9/17 20:16
 * @since
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface ApiVersion {

    /**
     * 分组名（即版本号），可以传入多个
     * @return
     */
    String[] value();
}

