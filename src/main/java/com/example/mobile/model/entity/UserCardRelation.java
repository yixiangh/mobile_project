package com.example.mobile.model.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户-卡券关联表
 */
@Data
public class UserCardRelation implements Serializable {

    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long userCardId;
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long userId;
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long cardId;

    private Date createTime;

    private Date updateTime;

}