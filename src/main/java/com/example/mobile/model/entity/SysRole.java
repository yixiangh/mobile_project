package com.example.mobile.model.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.example.mobile.model.vo.Pager;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色信息表
 */
@Data
public class SysRole extends Pager implements Serializable {

    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long roleId;

    private String roleName;

    private String roleCode;

    private String roleDesc;

    private String isDel;

    private Date createTime;

    private Date updateTime;

}