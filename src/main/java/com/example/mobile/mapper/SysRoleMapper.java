package com.example.mobile.mapper;

import com.example.mobile.model.entity.SysRole;
import com.example.mobile.model.vo.UserRoleMenu;

import javax.management.relation.RoleInfo;
import java.util.List;

public interface SysRoleMapper {
    int deleteByPrimaryKey(Integer roleId);

    int insert(SysRole record);

    SysRole selectByPrimaryKey(Integer roleId);

    List<SysRole> selectAll();

    int updateByPrimaryKey(SysRole record);

    List<SysRole> getRolesByUrm(UserRoleMenu urm);
}