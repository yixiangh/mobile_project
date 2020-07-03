package com.example.mobile.base.exception;

import com.example.mobile.utils.StringUtils;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 捕获Filter中抛出的异常
 */
@RestController
public class ErrorController extends BasicErrorController {

    public ErrorController() {
        super(new DefaultErrorAttributes(), new ErrorProperties());
    }

    @RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
        HttpStatus status = getStatus(request);
        int code = status.value();  //status.value():错误代码
        String msg = body.get("message").toString(); //body.get("message").toString()错误信息
        Map<String, Object> result = new HashMap<>();//自定义的错误信息类
        result.put("code", code);
        result.put("msg", msg);
        //TokenException Filter抛出的自定义错误类
        if (!StringUtils.isEmpty((String) body.get("exception")) && body.get("exception").equals(Exception.class.getName())) {
            body.put("status", HttpStatus.FORBIDDEN.value());
            status = HttpStatus.FORBIDDEN;
        }
        return new ResponseEntity<Map<String, Object>>(result, status);
    }

    @Override
    public String getErrorPath() {
        return "error/error";
    }

}
