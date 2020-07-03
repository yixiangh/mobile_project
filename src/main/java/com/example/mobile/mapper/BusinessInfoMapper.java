package com.example.mobile.mapper;

import com.example.mobile.model.entity.BusinessInfo;

import java.util.List;

public interface BusinessInfoMapper {
    int deleteByPrimaryKey(Integer busniessId);

    int insert(BusinessInfo record);

    BusinessInfo selectByPrimaryKey(Integer busniessId);

    List<BusinessInfo> selectAll();

    int updateByPrimaryKey(BusinessInfo record);
}