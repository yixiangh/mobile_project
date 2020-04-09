package com.example.mobile.mapper;

import com.example.mobile.model.entity.UserRole;
import java.util.List;

public interface UserRoleMapper {
    int deleteByPrimaryKey(Integer userRoleId);

    int insert(UserRole record);

    UserRole selectByPrimaryKey(Integer userRoleId);

    List<UserRole> selectAll();

    int updateByPrimaryKey(UserRole record);
}