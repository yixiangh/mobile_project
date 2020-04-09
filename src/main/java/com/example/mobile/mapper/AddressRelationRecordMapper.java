package com.example.mobile.mapper;

import com.example.mobile.model.entity.AddressRelationRecord;
import java.util.List;


public interface AddressRelationRecordMapper {
    int deleteByPrimaryKey(Integer userAddressId);

    int insert(AddressRelationRecord record);

    AddressRelationRecord selectByPrimaryKey(Integer userAddressId);

    List<AddressRelationRecord> selectAll();

    int updateByPrimaryKey(AddressRelationRecord record);
}