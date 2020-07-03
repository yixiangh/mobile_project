package com.example.mobile.model.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class RoleMenuRelation implements Serializable {

    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long roleMenuId;
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long roleId;
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long menuId;

    private Date createTime;
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long createBy;

    private static final long serialVersionUID = 1L;

}