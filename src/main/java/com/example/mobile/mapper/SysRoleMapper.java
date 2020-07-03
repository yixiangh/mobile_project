package com.example.mobile.mapper;

import com.example.mobile.model.entity.SysRole;
import com.example.mobile.model.vo.UserRoleMenu;
import com.github.pagehelper.Page;

import javax.management.relation.RoleInfo;
import java.util.List;

public interface SysRoleMapper {
    int deleteByPrimaryKey(SysRole role);

    int insert(SysRole record);

    SysRole selectByPrimaryKey(Integer roleId);

    int updateByPrimaryKey(SysRole record);

    List<SysRole> getRolesByUrm(UserRoleMenu urm);

    Page<SysRole> getRoleList(SysRole role);
}