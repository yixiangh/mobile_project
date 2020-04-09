package com.example.mobile.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户角色关联表
 */
@Data
public class UserRole implements Serializable {
    private Integer userRoleId;

    private Integer userId;

    private Integer roleId;

    private Date createTime;

    private Date updateTime;

}