package com.example.mobile.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.mobile.model.entity.MenuInfo;
import com.example.mobile.model.vo.MenuTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeUtil {
    /**
     * 将菜单集合转为树结构
     * @param rmrList
     * @param parentId
     * @return
     */
    public static List<MenuTree> converterMenuTree(List<MenuInfo> rmrList, Long parentId) {
        List<MenuTree> menuTreeList = new ArrayList<>();
        for (int i = 0; i < rmrList.size(); i++) {
            if (parentId == rmrList.get(i).getParentId())//如果是一级菜单
            {
                MenuTree menuTree = new MenuTree();
                Long menuId = rmrList.get(i).getMenuId();
                menuTree.setId(menuId);
                menuTree.setPid(rmrList.get(i).getParentId());
                menuTree.setLabel(rmrList.get(i).getMenuName());
                List<MenuTree> childrenArray = converterMenuTree(rmrList, menuId);
                menuTree.setChildren(childrenArray);
                menuTreeList.add(menuTree);
            }

        }
        return menuTreeList;
    }

    /**
     * 将菜单集合转为树结构（返回map）
     * @param rmrList
     * @param parentId
     * @return
     */
    public static List<Map<String, Object>> converterMenuTreeByMap(List<MenuInfo> rmrList, Long parentId) {
        List<Map<String, Object>> menuTreeList = new ArrayList<>();
        for (int i = 0; i < rmrList.size(); i++) {
            if (parentId == rmrList.get(i).getParentId())//如果是一级菜单
            {
                Map<String, Object> menuTree = new HashMap<>();
                Long menuId = rmrList.get(i).getMenuId();
                menuTree.put("value", menuId);
                menuTree.put("label", rmrList.get(i).getMenuName());
                List<Map<String, Object>> childrenArray = converterMenuTreeByMap(rmrList, menuId);
                menuTree.put("children", childrenArray);
                menuTreeList.add(menuTree);
            }

        }
        return menuTreeList;
    }

}
