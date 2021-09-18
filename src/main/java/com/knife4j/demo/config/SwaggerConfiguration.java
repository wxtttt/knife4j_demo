package com.knife4j.demo.config;

import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
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
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * @author wangxutao
 * @date 2021/6/30 20:38
 */
@Configuration
@EnableOpenApi
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfiguration implements InitializingBean {

    /**
     * 引入Knife4j提供的扩展类
     */
    private final OpenApiExtensionResolver openApiExtensionResolver;

    /**
     * 自定义注解使用
     */
    @Resource
    private ApplicationContext applicationContext;

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

    /*          以下是是实现自定义版本号注解              */

    /**
     * 构造docket
     * @param groupName
     * @return
     */
    private Docket buildDocket(String groupName) {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName(groupName)
                .select()
                .apis(method -> {
                    // 每个方法会进入这里进行判断并归类到不同分组，**请不要调换下面两段代码的顺序，在方法上的注解有优先级**
                    // 该方法上标注了版本
                    if (method.isAnnotatedWith(ApiVersion.class)) {
                        Optional<ApiVersion> apiVersion = method.findAnnotation(ApiVersion.class);
                        if (apiVersion.isPresent()) {
                            String[] values = apiVersion.get().value();
                            if (!Objects.equals(values.length, 0) && Arrays.asList(values).contains(groupName)) {
                                return Boolean.TRUE;
                            }
                        }
                    }

                    // 方法所在的类是否标注了版本?
                    Optional<ApiVersion> annotationOnClass = method.findControllerAnnotation(ApiVersion.class);
                    if (annotationOnClass.isPresent()) {
                        String[] values = annotationOnClass.get().value();
                        if (!Objects.equals(values.length, 0) && Arrays.asList(values).contains(groupName)) {
                            return Boolean.TRUE;
                        }
                    }
                    return Boolean.FALSE;
                })
                .paths(PathSelectors.any())
                .build()
                //赋予knife4j插件体系
                .extensions(openApiExtensionResolver.buildExtensions(groupName));
    }

    /**
     * 动态得创建Docket bean
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        // ApiVersionCst 里面定义的每个变量会成为一个docket
        Class<ApiVersionCst> clazz = ApiVersionCst.class;
        Field[] declaredFields = clazz.getDeclaredFields();

        // 动态注入bean
        AutowireCapableBeanFactory autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
        if (autowireCapableBeanFactory instanceof DefaultListableBeanFactory) {
            DefaultListableBeanFactory capableBeanFactory = (DefaultListableBeanFactory) autowireCapableBeanFactory;
            for (Field declaredField : declaredFields) {

                // 要注意 "工厂名和方法名"，意思是用这个bean的指定方法创建docket
                AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder
                        .genericBeanDefinition()
                        .setFactoryMethodOnBean("buildDocket", "swaggerConfiguration")
                        .addConstructorArgValue(declaredField.get(ApiVersionCst.class)).getBeanDefinition();
                capableBeanFactory.registerBeanDefinition(declaredField.getName(), beanDefinition);
            }
        }
    }
}
