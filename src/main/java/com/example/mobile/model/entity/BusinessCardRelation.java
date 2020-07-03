package com.example.mobile.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商家-卡券关联表
 */
@Data
public class BusinessCardRelation implements Serializable {
    private Long businessCardId;

    private Long businessId;

    private Long cardId;

    private Integer cardNum;

    private Date createTime;

    private Date updateTime;

}