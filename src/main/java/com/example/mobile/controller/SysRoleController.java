package com.example.mobile.controller;

import com.example.mobile.model.entity.SysRole;
import com.example.mobile.model.vo.Pager;
import com.example.mobile.service.SysMenuService;
import com.example.mobile.service.SysRoleService;
import com.example.mobile.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "role")
public class SysRoleController {

    @Autowired
    private SysRoleService roleService;
    @Autowired
    private SysMenuService menuService;

    @GetMapping(value = "roleList")
    public Result getRoleList(SysRole role) {
        Pager<SysRole> roleList = roleService.getRoleList(role);
        return Result.success(roleList);
    }

    @PostMapping(value = "addRole")
    public Result addRole(SysRole role) {
        int res = roleService.addRole(role);
        return Result.success();
    }

    @PostMapping(value = "editRole")
    public Result editRole(SysRole role) {
        int res = roleService.editRole(role);
        return Result.success();
    }

    @DeleteMapping(value = "delRole")
    public Result delRole(SysRole role) {
        int res = roleService.delRole(role);
        return Result.success();
    }


    /**
     * 获取角色菜单关联表
     * @return
     */
    @GetMapping(value = "getRoleMenuList")
    public Result getRoleMenuList(SysRole role) {
        Map<String, Object> result = roleService.getRoleMenuMap(role);
//        JSONArray roleMenus = roleService.getMenuByRole(role);//获取角色对应菜单
//        JSONArray menus = menuService.getMenuListForTree(new MenuInfo());//获取全部菜单
//        result.put("roleMenu",roleMenus);
//        result.put("allMenu",menus);
        return Result.success(result);
    }

    /**
     * 添加角色-权限关联信息
     * @return
     */
    @PostMapping(value = "addRoleMenu")
    public Result addRoleMenu(String menuArray, Long roleId) {
        int i = roleService.addRoleMenu(menuArray, roleId);
        return Result.success();
    }


}
