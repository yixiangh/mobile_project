package com.example.mobile.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商家-卡券关联表
 */
@Data
public class BusinessCardRelation implements Serializable {
    private String businessCardId;

    private String businessId;

    private String cardId;

    private Integer cardNum;

    private Date createTime;

    private Date updateTime;

}