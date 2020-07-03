package com.example.mobile.utils;

import lombok.Data;

/**
 * JWT载荷（定义JWT携带信息）
 */
@Data
public class MyClaim {

    private Long userId;
    private String userName;

}
