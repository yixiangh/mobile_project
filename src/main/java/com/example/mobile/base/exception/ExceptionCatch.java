package com.example.mobile.base.exception;

import com.example.mobile.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常捕获
 * 注意：仅能捕获到Controller层抛出的异常
 */
@Slf4j
@ControllerAdvice
public class ExceptionCatch {

    @ExceptionHandler(value = SystemException.class)//此注解表示定义该方法为全局异常捕获  意为自定义异常一定会被此方法捕获
    @ResponseBody
    public Result systemExceptionHandle(HttpServletRequest request, HttpServletResponse response,Exception e)
    {
        SystemException systemException = (SystemException) e;
        return Result.fial(systemException.getErrorCode(),systemException.getErrorDes());
    }

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public Result runtimeExceptionHandle(HttpServletRequest request, HttpServletResponse response,Exception e)
    {
        log.error(String.valueOf(e.getStackTrace()));
        return Result.fial(500,"异常请求："+request.getRequestURL()+"，异常信息为："+e.getMessage());
    }

}
