package com.example.mobile.service;

import com.alibaba.fastjson.JSONArray;
import com.example.mobile.model.entity.MenuInfo;
import com.example.mobile.model.entity.RoleMenuRelation;
import com.example.mobile.model.entity.SysRole;
import com.example.mobile.model.vo.Pager;

import java.util.List;
import java.util.Map;

public interface SysRoleService {
    Pager<SysRole> getRoleList(SysRole role);

    int editRole(SysRole role);

    int addRole(SysRole role);

    int delRole(SysRole role);

    int addRoleMenu(String menuArray, Long roleId);

    Map<String, Object> getRoleMenuMap(SysRole role);
}
