package com.example.mobile.mapper;

import com.example.mobile.model.entity.UserCardRelation;
import java.util.List;

public interface UserCardRelationMapper {
    int deleteByPrimaryKey(String userCardId);

    int insert(UserCardRelation record);

    UserCardRelation selectByPrimaryKey(String userCardId);

    List<UserCardRelation> selectAll();

    int updateByPrimaryKey(UserCardRelation record);
}