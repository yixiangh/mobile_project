package com.example.mobile.mapper;

import com.example.mobile.model.entity.MenuInfo;
import com.example.mobile.model.entity.RoleMenuRelation;
import com.example.mobile.model.entity.SysRole;

import java.util.List;

public interface RoleMenuRelationMapper {
    int deleteByRole(Long roleMenuId);

    int insert(List<RoleMenuRelation> rmrList);

    RoleMenuRelation selectByPrimaryKey(String roleMenuId);

    List<RoleMenuRelation> selectAll();

    int updateByPrimaryKey(RoleMenuRelation record);

    List<MenuInfo> getMenuByRole(SysRole role);
}