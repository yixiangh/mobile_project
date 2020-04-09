package com.example.mobile.mapper;

import com.example.mobile.model.entity.RoleMenuRelation;
import java.util.List;

public interface RoleMenuRelationMapper {
    int deleteByPrimaryKey(String roleMenuId);

    int insert(RoleMenuRelation record);

    RoleMenuRelation selectByPrimaryKey(String roleMenuId);

    List<RoleMenuRelation> selectAll();

    int updateByPrimaryKey(RoleMenuRelation record);
}