package com.example.mobile.mapper;

import com.example.mobile.model.entity.MenuInfo;
import com.example.mobile.model.entity.SysRole;
import com.github.pagehelper.Page;

import java.util.List;

public interface MenuInfoMapper {
    int insert(MenuInfo record);

    MenuInfo selectByPrimaryKey(Long menuId);

    int edit(MenuInfo record);

    List<MenuInfo> getMenusByRoles(List<SysRole> roleList, String isDel);

    Page<MenuInfo> getMenuList(MenuInfo menu);

    int delete(MenuInfo menu);

    List<MenuInfo> getMenuListForTree(MenuInfo menu);
}