package com.example.mobile.base.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截所有请求，验证Token
 */
@Slf4j
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        if (HttpMethod.OPTIONS.equals(request.getMethod()))//如果是预请求，则放过
//        {
//            response.setStatus(HttpStatus.NO_CONTENT.value());//返回一个空内容
//            return false;
//        }
//        response.setCharacterEncoding("UTF-8");
//        String jwtToken = request.getHeader(JwtConstant.JWT_TOKEN);
//        if (StringUtils.isNotEmpty(jwtToken))   // 如果请求中带有token
//        {
//            boolean bool = JwtUtil.verif(jwtToken);//验证Token
//            if (bool)
//            {
//                return true;
//            }
//        }
//        //未携带token
//        response.setContentType("application/json;charset=utf-8");
//        try {
//            JSONObject json = new JSONObject();
//            json.put("code",403);
//            json.put("msg","认证失败，请重新登录！");
//            response.getWriter().append(json.toJSONString());
//            log.warn("认证失败,请求地址为："+request.getRequestURI());
//            return false;
//        }catch (Exception e)
//        {
//            e.printStackTrace();
//            response.sendError(500);
//            return false;
//        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.afterConcurrentHandlingStarted(request, response, handler);
    }
}
