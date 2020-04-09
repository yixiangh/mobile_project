package com.example.mobile.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 地址-用户/商家关联记录表
 */
@Data
public class AddressRelationRecord implements Serializable {
    private Integer userAddressId;

    private Integer relationId;

    private String addrProvince;

    private String addrCity;

    private String addrCounty;

    private String addrTownship;

    private String addrVillage;

    private String phone;

    private String contacts;

    private String isDel;

    private Date createTime;

    private Date updateTime;

}