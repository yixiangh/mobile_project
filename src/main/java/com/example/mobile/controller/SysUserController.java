package com.example.mobile.controller;

import com.example.mobile.model.entity.SysUser;
import com.example.mobile.model.entity.UserRoleRelation;
import com.example.mobile.model.vo.Pager;
import com.example.mobile.service.SysUserService;
import com.example.mobile.utils.GenerateIdUtil;
import com.example.mobile.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 系统用户管理
 */
@RestController
@RequestMapping(value = "user")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @RequestMapping(value = "getSysUserList")
//    @RequiresPermissions(value = "vegetable_select")
    public Result getSysUserList(SysUser user) {
        Pager<SysUser> pager = sysUserService.getSysUserList(user);
        return Result.success(pager);
    }

    @PostMapping(value = "insertSysUser")
    public Result insertSysUser(SysUser sysUser) {
        if (sysUser != null) {
            sysUser.setUserId(new GenerateIdUtil(1l, 1l).nextId());
            sysUser.setCreateTime(new Date());
            sysUser.setUpdateTime(new Date());
            try {
                int res = sysUserService.insertSysUser(sysUser);
                return Result.success();
            } catch (Exception e) {
                e.printStackTrace();
                return Result.fial("操作失败");
            }
        }
        return Result.fial(501, "请输入参数");
    }

    /**
     * 编辑用户信息
     * @return
     */
    @PostMapping(value = "editUser")
    public Result eidtUser(SysUser user) {
        int res = sysUserService.editUser(user);
        if (res > 0) {
            return Result.success();
        } else {
            return Result.fial("操作失败，请重试！");
        }

    }

    /**
     * 删除用户信息
     * @return
     */
    @DeleteMapping(value = "delUser")
    public Result delUser(SysUser user) {
        int res = sysUserService.delUser(user);
        if (res > 0) {
            return Result.success();
        } else {
            return Result.fial("操作失败，请重试！");
        }

    }

    /**
     * 添加用户-角色关联信息
     * @param userRole
     * @return
     */
    @PostMapping(value = "addUserRole")
    public Result addUserRole(UserRoleRelation userRole) {
        int res = sysUserService.addUserRole(userRole);
        return Result.success();
    }

    /**
     * 修改用户-角色关联信息
     * @param userRole
     * @return
     */
    @PostMapping(value = "editUserRole")
    public Result editUserRole(UserRoleRelation userRole) {
        int res = sysUserService.editUserRole(userRole);
        return Result.success();
    }

    /**
     * 删除用户-角色关联信息
     * @param userRole
     * @return
     */
    @PostMapping(value = "delUserRole")
    public Result delUserRole(UserRoleRelation userRole) {
        int res = sysUserService.delUserRole(userRole);
        return Result.success();
    }
}
