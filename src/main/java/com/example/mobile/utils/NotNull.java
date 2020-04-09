package com.example.mobile.utils;

import java.lang.annotation.*;

/**
 * 非空验证（请求参数非空验证）
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NotNull {
    String param() default "";
}
