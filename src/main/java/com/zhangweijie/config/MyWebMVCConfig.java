package com.zhangweijie.config;

import org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zhangweijie, E-mail:zhangweijiepku@gmail.com
 * @create 2021-01-14 20:29
 */
@Configuration
public class MyWebMVCConfig implements WebMvcConfigurer {
    //自定义配置：
    //实现文件上传时弃用Spring默认的StandardServletMultipartResolver,改用commons-fileupload依赖的CommonsMultipartResolver
    //需要导入commons-fileupload依赖,并在config中配置MultipartResolver
    @Bean(name = "multipartResolver")
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setDefaultEncoding("utf-8");
        multipartResolver.setMaxUploadSize(10485760);
        multipartResolver.setMaxInMemorySize(40960);
        return multipartResolver;
    }


}
