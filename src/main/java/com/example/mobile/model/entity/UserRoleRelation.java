package com.example.mobile.model.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserRoleRelation implements Serializable {
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long userRoleId;
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long userId;
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long roleId;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

}