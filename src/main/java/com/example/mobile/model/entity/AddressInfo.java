package com.example.mobile.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 地址信息表
 */
@Data
public class AddressInfo implements Serializable {
    private String addressId;

    private Integer addressType;

    private Integer addressLon;

    private Integer addressLat;

    private String addressName;

    private String addressCode;

    private String addressDesc;

    private Integer isDel;

    private Date createTime;

    private Date updateTime;

}