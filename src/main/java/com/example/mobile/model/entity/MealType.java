package com.example.mobile.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 膳食分类（早餐/午餐/晚餐/宵夜）
 */
@Data
public class MealType implements Serializable {
    private Long typeId;

    private String typeName;

    private String typeCode;

    private String typeIcon;

    private Date createTime;

    private Date updateTime;

}