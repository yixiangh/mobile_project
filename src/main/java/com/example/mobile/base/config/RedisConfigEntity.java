package com.example.mobile.base.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RedisConfigEntity {

    @Value("${spring.redis.host}")
    private String host;
    @Value(value = "${spring.redis.port}")
    private int port;
    @Value(value = "${spring.redis.timeout}")
    private int timeout;
    //    @Value(value = "${spring.redis.password}")
//    private String password;
    @Value(value = "${spring.redis.expire}")
    private int expire;

    public String getHost() {
        return host;
    }
}
