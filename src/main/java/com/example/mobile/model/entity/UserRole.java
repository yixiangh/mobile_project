package com.example.mobile.model.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户角色关联表
 */
@Data
public class UserRole implements Serializable {

    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long userRoleId;
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long userId;
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long roleId;

    private Date createTime;

    private Date updateTime;

}