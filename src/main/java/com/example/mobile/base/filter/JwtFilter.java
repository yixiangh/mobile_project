package com.example.mobile.base.filter;

import com.example.mobile.constance.JwtConstant;
import com.example.mobile.utils.JwtToken;
import com.example.mobile.utils.JwtUtil;
import com.example.mobile.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
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


    /**
     * CROS复杂请求时会先发送一个OPTIONS请求，来测试服务器是否支持本次请求，这个请求时不带数据的，请求成功后才会发送真实的请求。
     * 所以第一次发送请求要确认服务器支不支持接收这个header。第二次才会传数据，所以我们要做的就是把所有的OPTIONS请求统统放行
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
//        httpServletResponse.setHeader('Access-Control-Allow-Headers', 'Content-Type, Content-Length, Authorization, Accept, X-Requested-With , yourHeaderFeild');
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    /**
     * isAccessAllowed：表示是否允许访问
     * 如果允许访问返回true，否则false；
     * @param servletRequestre
     * @param servletResponse
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequestre, ServletResponse servletResponse, Object mappedValue) {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequestre;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        String token = request.getHeader(JwtConstant.JWT_TOKEN);
        if (StringUtils.isEmpty(token)) {
            throw new NullPointerException("token值不能为空！");
        }
        if (!JwtUtil.verif(token)) {
            throw new AuthenticationException("token无效");
        }
        try {
            return executeLogin(servletRequestre, servletResponse);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("JwtFilter执行executeLogin出错，错误信息为：" + e.getMessage());
            response401(request, response);
        }
        //如果请求头不存在 Token，则可能是执行登陆操作或者是游客状态访问，无需检查 token，直接返回 true
        return true;
    }

    @Override
    protected boolean executeLogin(ServletRequest servletRequestre, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequestre;
        String token = request.getHeader(JwtConstant.JWT_TOKEN);
//        String rememberMe = request.getHeader(JwtConstant.REMEMBERME);
        JwtToken jwtToken = new JwtToken(token, false);
        getSubject(servletRequestre, servletResponse).login(jwtToken);// 提交给realm进行登入，如果错误他会抛出异常并被捕获
        return true;// 如果没有抛出异常则代表登入成功，返回true
    }

    /**
     * 将非法请求跳转到 /401
     */
    private void response401(ServletRequest req, ServletResponse resp) {
        try {
            HttpServletRequest request = (HttpServletRequest) req;
            HttpServletResponse response = (HttpServletResponse) resp;
            try {
                request.getRequestDispatcher("/401").forward(request, response);
            } catch (ServletException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
