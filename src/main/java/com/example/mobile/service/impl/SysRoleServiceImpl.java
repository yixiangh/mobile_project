package com.example.mobile.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.mobile.base.exception.SystemException;
import com.example.mobile.constance.Constant;
import com.example.mobile.mapper.MenuInfoMapper;
import com.example.mobile.mapper.RoleMenuRelationMapper;
import com.example.mobile.mapper.SysRoleMapper;
import com.example.mobile.model.entity.MenuInfo;
import com.example.mobile.model.entity.RoleMenuRelation;
import com.example.mobile.model.entity.SysRole;
import com.example.mobile.model.vo.Pager;
import com.example.mobile.service.SysRoleService;
import com.example.mobile.utils.GenerateIdUtil;
import com.example.mobile.utils.StringUtils;
import com.example.mobile.utils.TreeUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private RoleMenuRelationMapper roleMenuRelationMapper;

    @Autowired
    private MenuInfoMapper menuInfoMapper;

    @Override
    public Pager<SysRole> getRoleList(SysRole role) {
        role.setIsDel(Constant.IS_DEL_FALSE);
        PageHelper.startPage(role.getPageNum(), role.getPageSize());
        Page<SysRole> roleList = roleMapper.getRoleList(role);
        Pager result = new Pager();
        result.setData(roleList);
        result.setCount(roleList.getTotal());
        return result;
    }

    @Override
    public int editRole(SysRole role) {
        if (StringUtils.isEmpty(role.getRoleId())) {
            throw new SystemException(1012);
        }
        return roleMapper.updateByPrimaryKey(role);
    }

    @Override
    public int addRole(SysRole role) {
        if (StringUtils.isEmpty(role.getRoleCode()) || StringUtils.isEmpty(role.getRoleName())) {
            throw new SystemException(1012);
        }
        role.setIsDel(Constant.IS_DEL_FALSE);
        role.setCreateTime(new Date());
        role.setRoleId(new GenerateIdUtil(1l, 1l).nextId());
        role.setUpdateTime(new Date());
        try {
            return roleMapper.insert(role);
        } catch (Exception e) {
            throw new SystemException(500, e.getMessage());
        }
    }

    @Override
    public int delRole(SysRole role) {
        if (StringUtils.isEmpty(role.getRoleId())) {
            throw new SystemException(1012);
        }
        try {
            role.setIsDel(Constant.IS_DEL_TRUE);
            role.setUpdateTime(new Date());
            return roleMapper.deleteByPrimaryKey(role);
        } catch (Exception e) {
            throw new SystemException(500, e.getMessage());
        }
    }

    @Override
    public int addRoleMenu(String menuArray, Long roleId) {
        if (roleId == null) {
            throw new SystemException(1016);
        }
        try {
            roleMenuRelationMapper.deleteByRole(roleId);
            JSONArray jsonArray = JSON.parseArray(menuArray);
            if (jsonArray != null) {
                List<RoleMenuRelation> rmrList = new ArrayList<>();
                GenerateIdUtil generateIdUtil = new GenerateIdUtil(1, 1);//ID生成对象
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    RoleMenuRelation rmr = new RoleMenuRelation();
                    rmr.setCreateTime(new Date());
                    rmr.setMenuId(jsonObject.getLong("id"));
                    rmr.setRoleId(roleId);
                    rmr.setCreateBy(1L);
                    rmr.setRoleMenuId(generateIdUtil.nextId());
                    rmrList.add(rmr);
                }

                return roleMenuRelationMapper.insert(rmrList);
            }
        } catch (Exception e) {
            throw new SystemException(500);
        }
        return -1;
    }

    @Override
    public Map<String, Object> getRoleMenuMap(SysRole role) {
        Map<String, Object> result = new HashMap<>();
        List<MenuInfo> roleMenus = roleMenuRelationMapper.getMenuByRole(role);//当前角色拥有的菜单权限
        MenuInfo menu = new MenuInfo();
        menu.setIsDel(Constant.IS_DEL_FALSE);
        List<MenuInfo> allMenus = menuInfoMapper.getMenuListForTree(menu);//所有的菜单权限
        result.put("roleMenu", TreeUtil.converterMenuTree(roleMenus, Constant.FIRST_LEVEL_PARENT_ID));
        result.put("allMenu", TreeUtil.converterMenuTree(allMenus, Constant.FIRST_LEVEL_PARENT_ID));
        return result;
    }


}
