package com.example.mobile.base.interceptor;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 非空判断拦截器
 */
public class NotNullInterceptor extends HandlerInterceptorAdapter {

    public NotNullInterceptor() {
        super();
    }

    /**
     * 在请求处理之前进行调用Controller方法调用之前
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
        System.out.println(obj instanceof HandlerMethod);
        if (!(obj instanceof HandlerMethod))//如果方法没有使用注解  直接跳过
        {
            return true;
        }
//        HandlerMethod handlerMethod = (HandlerMethod) obj;
//        NotNull notNulla = handlerMethod.getMethodAnnotation(NotNull.class);
//        Method method = handlerMethod.getMethod();
//        if (method.getAnnotation(NotNull.class) != null)
//        {
//            NotNull notNull = method.getAnnotation(NotNull.class);
//            String param = notNull.param();
//            //从HttpServletRequest获取注解上指定参数
//            Object paramVal = request.getParameter(param);
//            if (null != paramVal)
//            {
//                return true;
//            }else
//            {
//                response.getWriter().write(JSONObject.toJSONString("参数不能为空！"));
//                return false;
//            }
//        }
        else {
//            return false;
            return true;
        }
    }

    /**
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

    }
}
