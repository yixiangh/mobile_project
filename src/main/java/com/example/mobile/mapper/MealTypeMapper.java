package com.example.mobile.mapper;

import com.example.mobile.model.entity.MealType;

import java.util.List;

public interface MealTypeMapper {
    int deleteByPrimaryKey(String typeId);

    int insert(MealType record);

    MealType selectByPrimaryKey(String typeId);

    List<MealType> selectAll();

    int updateByPrimaryKey(MealType record);
}