package com.example.mobile.mapper;

import com.example.mobile.model.entity.MenuInfo;
import com.example.mobile.model.entity.SysRole;

import javax.management.relation.RoleInfo;
import java.util.List;

public interface MenuInfoMapper {
    int deleteByPrimaryKey(String menuId);

    int insert(MenuInfo record);

    MenuInfo selectByPrimaryKey(String menuId);

    List<MenuInfo> selectAll();

    int updateByPrimaryKey(MenuInfo record);

    List<MenuInfo> getMenusByRoles(List<SysRole> roleList, int isDel);
}