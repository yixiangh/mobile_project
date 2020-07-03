package com.example.mobile.model.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.example.mobile.model.vo.Pager;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MenuInfo extends Pager implements Serializable {

    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long menuId;

    private String menuName;

    private Integer menuType;

    private String menuIcon;

    private String menuUrl;

    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long parentId;

    private String parentName;

    private String menuCode;

    private Integer menuOrder;

    private String isDel;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

}