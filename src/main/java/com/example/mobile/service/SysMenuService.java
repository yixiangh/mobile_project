package com.example.mobile.service;

import com.example.mobile.model.entity.MenuInfo;
import com.example.mobile.model.vo.MenuTree;
import com.example.mobile.model.vo.Pager;

import java.util.List;
import java.util.Map;


public interface SysMenuService {
    Pager<MenuInfo> getMenuList(MenuInfo menu);

    int insert(MenuInfo menu);

    int edit(MenuInfo menu);

    int delete(MenuInfo menu);

    List<Map<String, Object>> getAllMenuTree(MenuInfo menu);
}
