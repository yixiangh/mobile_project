package com.example.mobile.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商家信息表
 */
@Data
public class BusinessInfo implements Serializable {
    private Long busniessId;

    private String businessName;

    private String businessCode;

    private Integer businessType;

    private String businessAddress;

    private Integer businessStatus;

    private String isDel;

    private String businessMan;

    private String businessPhone;

    private String businessLicense;

    private Date createTime;

    private Date updateTime;

}