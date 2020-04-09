package com.example.mobile.mapper;

import com.example.mobile.model.entity.UserRoleRelation;
import java.util.List;

public interface UserRoleRelationMapper {
    int deleteByPrimaryKey(Integer userRoleId);

    int insert(UserRoleRelation record);

    UserRoleRelation selectByPrimaryKey(Integer userRoleId);

    List<UserRoleRelation> selectAll();

    int updateByPrimaryKey(UserRoleRelation record);
}