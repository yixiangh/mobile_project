package com.example.mobile.service.impl;

import com.example.mobile.constance.Constant;
import com.example.mobile.mapper.MenuInfoMapper;
import com.example.mobile.model.entity.MenuInfo;
import com.example.mobile.model.vo.MenuTree;
import com.example.mobile.model.vo.Pager;
import com.example.mobile.service.SysMenuService;
import com.example.mobile.utils.GenerateIdUtil;
import com.example.mobile.utils.TreeUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private MenuInfoMapper menuMapper;

    @Override
    public Pager<MenuInfo> getMenuList(MenuInfo menu) {
        menu.setIsDel(Constant.IS_DEL_FALSE);
        PageHelper.startPage(menu.getPageNum(), menu.getPageSize());
        Page<MenuInfo> menuList = menuMapper.getMenuList(menu);
        Pager result = new Pager();
        result.setCount(menuList.getTotal());
        result.setData(menuList);
        return result;
    }

    @Override
    public int insert(MenuInfo menu) {
        menu.setIsDel(Constant.IS_DEL_FALSE);
        menu.setCreateTime(new Date());
        menu.setUpdateTime(new Date());
        menu.setMenuId(new GenerateIdUtil(1l, 1l).nextId());
        if (menu.getParentId() == null) {
            menu.setParentId(0l);
        }
        return menuMapper.insert(menu);
    }

    @Override
    public int edit(MenuInfo menu) {
        menu.setIsDel(Constant.IS_DEL_FALSE);
        menu.setUpdateTime(new Date());
        return menuMapper.edit(menu);
    }

    @Override
    public int delete(MenuInfo menu) {
        menu.setIsDel(Constant.IS_DEL_TRUE);
        menu.setUpdateTime(new Date());
        return menuMapper.delete(menu);
    }

    @Override
    public List<Map<String, Object>> getAllMenuTree(MenuInfo menu) {
        menu.setIsDel(Constant.IS_DEL_FALSE);
        List<MenuInfo> menuList = menuMapper.getMenuListForTree(menu);
        if (menuList != null && menuList.size() > 0) {
            List<Map<String, Object>> treeList = TreeUtil.converterMenuTreeByMap(menuList, Constant.FIRST_LEVEL_PARENT_ID);
            return treeList;
        }
        return null;
    }

}
