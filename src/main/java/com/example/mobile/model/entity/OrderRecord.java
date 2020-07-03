package com.example.mobile.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单记录表
 */
@Data
public class OrderRecord implements Serializable {
    private Long orderId;

    private String orderName;

    private String orderDesc;

    private Long userId;

    private Integer orderStatus;

    private Integer payStatus;

    private Date createTime;

    private Date updateTime;

}