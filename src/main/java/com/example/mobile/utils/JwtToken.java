package com.example.mobile.utils;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.RememberMeAuthenticationToken;

/**
 * Shiro 仅提供了一个可以直接使用的 UsernamePasswordToken，用于实现基于用户名/密码主体（Subject）身份认证。
 * UsernamePasswordToken实现了 RememberMeAuthenticationToken 和 HostAuthenticationToken，可以实现“记住我”及“主机验证”的支持。
 * 一般情况下UsernamePasswordToken已经可以满足我们的大我数需求。当我们遇到需要声明自己的Token类时，可以根据需求来实现AuthenticationToken，HostAuthenticationToken或RememberMeAuthenticationToken。
 * 如果不需要“记住我”，也不需要“主机验证”，则可以实现AuthenticationToken；
 * 如果需要“记住我”，则可以实现RememberMeAuthenticationToken；
 * 如果需要“主机验证”功能，则可以实现HostAuthenticationToken；
 * 如果需要“记住我”，且需要“主机验证”，则可以像UsernamePasswordToken一样，同时实现RememberMeAuthenticationToken和HostAuthenticationToken。
 * 如果需要其他自定义功能，则需要自己实现。
 */
public class JwtToken implements RememberMeAuthenticationToken {

    private String token;
    private boolean rememberMe;

    public JwtToken(String token,boolean rememberMe)
    {
        this.token = token;
        this.rememberMe = rememberMe;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public boolean isRememberMe() {
        return rememberMe;
    }
}
