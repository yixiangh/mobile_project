package com.example.mobile.mapper;

import com.example.mobile.model.entity.CardType;
import java.util.List;

public interface CardTypeMapper {
    int deleteByPrimaryKey(String typeId);

    int insert(CardType record);

    CardType selectByPrimaryKey(String typeId);

    List<CardType> selectAll();

    int updateByPrimaryKey(CardType record);
}