package com.example.mobile.controller;

import com.example.mobile.model.entity.MenuInfo;
import com.example.mobile.model.vo.Pager;
import com.example.mobile.service.SysMenuService;
import com.example.mobile.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "menu")
public class SysMenuController {

    @Autowired
    private SysMenuService menuService;

    @GetMapping(value = "menuList")
    public Result getMenuList(MenuInfo menu) {
        Pager<MenuInfo> menuList = menuService.getMenuList(menu);
        return Result.success(menuList);
    }

    @PostMapping(value = "insertMenu")
    public Result insert(MenuInfo menu) {
        int i = menuService.insert(menu);
        return Result.success();
    }

    @PostMapping(value = "edit")
    public Result edit(MenuInfo menu) {
        int i = menuService.edit(menu);
        return Result.success();
    }

    @PostMapping(value = "delete")
    public Result delete(MenuInfo menu) {
        int i = menuService.delete(menu);
        return Result.success();
    }

    @GetMapping(value = "allMenuTree")
    public Result allMenuTree(MenuInfo menu) {
        List<Map<String, Object>> treeList = menuService.getAllMenuTree(menu);
        return Result.success(treeList);
    }

}
