package com.example.mobile.mapper;

import com.example.mobile.model.entity.BusinessCardRelation;
import java.util.List;


public interface BusinessCardRelationMapper {
    int deleteByPrimaryKey(String businessCardId);

    int insert(BusinessCardRelation record);

    BusinessCardRelation selectByPrimaryKey(String businessCardId);

    List<BusinessCardRelation> selectAll();

    int updateByPrimaryKey(BusinessCardRelation record);
}