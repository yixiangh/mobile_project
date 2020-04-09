package com.example.mobile.mapper;

import com.example.mobile.model.entity.CardInfo;
import java.util.List;

public interface CardInfoMapper {
    int deleteByPrimaryKey(String cardId);

    int insert(CardInfo record);

    CardInfo selectByPrimaryKey(String cardId);

    List<CardInfo> selectAll();

    int updateByPrimaryKey(CardInfo record);
}