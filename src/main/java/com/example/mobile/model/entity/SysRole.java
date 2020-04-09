package com.example.mobile.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色信息表
 */
@Data
public class SysRole implements Serializable {
    private Integer roleId;

    private String roleName;

    private String roleCode;

    private String roleDesc;

    private Integer isDel;

    private Date createTime;

    private Date updateTime;

}