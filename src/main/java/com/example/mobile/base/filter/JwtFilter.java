package com.example.mobile.base.filter;

import com.alibaba.fastjson.JSONObject;
import com.example.mobile.base.constant.JwtConstant;
import com.example.mobile.base.exception.SystemException;
import com.example.mobile.utils.JwtToken;
import com.example.mobile.utils.JwtUtil;
import com.example.mobile.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 由于Shiro默认是基于Session进行验证的，如果要使用JWT
 * 需要自定义一个过滤器 并继承BasicHttpAuthenticationFilter 并对部分原方法进行了重写 是为了告诉Shiro使用JWTToken而不是自身的
 * 该过滤器主要有三步：
 * 1.检验请求头是否带有 token ((HttpServletRequest) request).getHeader("Token") != null
 * 2.如果带有 token，执行 shiro 的 login() 方法，将 token 提交到 Realm 中进行检验；如果没有 token，说明当前状态为游客状态（或者其他一些不需要进行认证的接口）
 * 3.如果在 token 校验的过程中出现错误，如 token 校验失败，那么我会将该请求视为认证不通过，则重定向到 /unauthorized/**
 */
@Slf4j
public class JwtFilter extends BasicHttpAuthenticationFilter {

    @Override
    @Qualifier(value = "SystemException")
    protected boolean isAccessAllowed(ServletRequest servletRequestre, ServletResponse servletResponse, Object mappedValue) {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequestre;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        String token = request.getHeader(JwtConstant.JWT_TOKEN);
        if (StringUtils.isEmpty(token))
        {
            throw new NullPointerException("token值不能为空！");
        }
        if (!JwtUtil.verif(token))
        {
            throw new AuthenticationException("token无效");
        }
        try {
            return executeLogin(servletRequestre,servletResponse);
        }catch (Exception e) {
            e.printStackTrace();
            log.error("JwtFilter执行executeLogin出错，错误信息为："+e.getMessage());
        }
        //如果请求头不存在 Token，则可能是执行登陆操作或者是游客状态访问，无需检查 token，直接返回 true
        return true;
    }

    @Override
    protected boolean executeLogin(ServletRequest servletRequestre, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequestre;
        String token = request.getHeader(JwtConstant.JWT_TOKEN);
//        String rememberMe = request.getHeader(JwtConstant.REMEMBERME);
        JwtToken jwtToken = new JwtToken(token,false);
        getSubject(servletRequestre,servletResponse).login(jwtToken);// 提交给realm进行登入，如果错误他会抛出异常并被捕获
        return true;// 如果没有抛出异常则代表登入成功，返回true
    }
}
