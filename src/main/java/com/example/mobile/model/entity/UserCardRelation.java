package com.example.mobile.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户-卡券关联表
 */
@Data
public class UserCardRelation implements Serializable {
    private String userCardId;

    private String userId;

    private String cardId;

    private Date createTime;

    private Date updateTime;

}