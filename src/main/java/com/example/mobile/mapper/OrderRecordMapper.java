package com.example.mobile.mapper;

import com.example.mobile.model.entity.OrderRecord;
import java.util.List;

public interface OrderRecordMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(OrderRecord record);

    OrderRecord selectByPrimaryKey(String orderId);

    List<OrderRecord> selectAll();

    int updateByPrimaryKey(OrderRecord record);
}