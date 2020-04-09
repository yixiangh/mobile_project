package com.example.mobile.base.config;

import com.example.mobile.utils.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

/**
 * 配置Environment，读取配置文件内容
 */
@Configuration
@PropertySource(value = {"classpath:errorCode.properties"},encoding = "UTF-8")
public class PropertiesConfig {

    @Autowired
    private Environment env;

    /**
     * 使用@PostConstruct注解将Environment初始化
     * 被@PostConstruct修饰的方法会在服务器加载Servlet的时候运行，
     * 并且只会被服务器调用一次，类似于Serclet的inti()方法。
     * 被@PostConstruct修饰的方法会在构造函数之后，init()方法之前运行。
     */
    @PostConstruct
    public void setEnv()
    {
        PropertiesUtil.setEnv(env);
    }

}
