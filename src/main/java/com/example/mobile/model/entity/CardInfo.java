package com.example.mobile.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 卡券信息表
 */
@Data
public class CardInfo implements Serializable {
    private Long cardId;

    private String cardName;

    private String cardCode;

    private Long cardTypeId;

    private Date createTime;

    private Date updateTime;

}