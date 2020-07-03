package com.example.mobile.mapper;

import com.example.mobile.model.entity.AddressInfo;

import java.util.List;


public interface AddressInfoMapper {
    int deleteByPrimaryKey(String addressId);

    int insert(AddressInfo record);

    AddressInfo selectByPrimaryKey(String addressId);

    List<AddressInfo> selectAll();

    int updateByPrimaryKey(AddressInfo record);
}