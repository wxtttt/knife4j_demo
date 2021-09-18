package com.knife4j.demo.config;

import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import javax.annotation.Resource;

/**
 * @author wangxutao
 * @date 2021/6/30 20:38
 */
@Configuration
@EnableOpenApi
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfiguration {

    /*引入Knife4j提供的扩展类*/
    private final OpenApiExtensionResolver openApiExtensionResolver;

    @Autowired
    public SwaggerConfiguration(OpenApiExtensionResolver openApiExtensionResolver) {
        this.openApiExtensionResolver = openApiExtensionResolver;
    }

    /**
     * swagger2配置
     * @return
     */
    @Bean
    public Docket defaultApi() {
        String groupName = "default(所有controller)";
        return new Docket(DocumentationType.SWAGGER_2)
                // 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）
                .apiInfo(apiInfo())
                .groupName(groupName)
                // 设置哪些接口暴露给Swagger展示
                .select()
                // (第一种方式)扫描指定包中的swagger注解
                .apis(RequestHandlerSelectors.basePackage("com.knife4j.demo.controller"))
                // (第二种方式)扫描所有
                //.apis(RequestHandlerSelectors.any())
                // (第三种方式)扫描所有有注解的api
                // .apis( RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                // 筛选指定url的类
                // .paths(PathSelectors.ant("/user/*"))
                .build()
                //赋予knife4j插件体系
                .extensions(openApiExtensionResolver.buildExtensions(groupName));

    }

    /**
     * 添加摘要信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 设置标题
                .title("######## 这里是标题 ########")
                // 描述
                .description("######## 这里是简介 ########")
                // 作者信息
                .contact(new Contact("wangxutao","http://www.xx.com/","1780670620@qq.com"))
                // 版本
                .version("1.0")
                .build();
    }


    @Bean
    public Docket defaultApi3() {
        String groupName = "筛选/user/**的版本";
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfoUser())
                .groupName(groupName)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.knife4j.demo.controller"))
                .paths(PathSelectors.ant("/user/*"))
                .build()
                //赋予knife4j插件体系
                .extensions(openApiExtensionResolver.buildExtensions(groupName));
    }

    /**
     * 自定义其他信息
     * @return
     */
    private ApiInfo apiInfoUser() {
        return new ApiInfoBuilder()
                .title(" 只筛选/user/**")
                .description("# user简介")
                .contact(new Contact("wangxutao","http://www.xx.com/","1780670620@qq.com"))
                .version("1.0")
                .build();
    }


}
