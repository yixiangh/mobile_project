package com.example.mobile.base.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义拦截器注册配置
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new NotNullInterceptor()).addPathPatterns("/**");

        List<String> excludePath = new ArrayList<>();//定义无需验证接口集合
        excludePath.add("/user_register"); //注册
        excludePath.add("/login"); //登录
        excludePath.add("/logout"); //登出
        excludePath.add("/static/**");  //静态资源
        excludePath.add("/assets/**");  //静态资源
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(excludePath);
    }
}
