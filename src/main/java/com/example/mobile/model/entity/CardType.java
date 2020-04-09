package com.example.mobile.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 卡券类型信息表
 */
@Data
public class CardType implements Serializable {
    private String typeId;

    private String typeName;

    private String typeCode;

    private String typeDesc;

    private Date createTime;

    private Date updateTime;

}