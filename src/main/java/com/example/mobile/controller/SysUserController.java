package com.example.mobile.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.mobile.base.exception.SystemException;
import com.example.mobile.model.entity.SysUser;
import com.example.mobile.model.entity.UserRoleRelation;
import com.example.mobile.model.vo.UserRoleMenu;
import com.example.mobile.service.SysUserService;
import com.example.mobile.utils.NotNull;
import com.example.mobile.utils.Result;
import com.example.mobile.utils.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * 系统用户管理
 */
@RestController
@RequestMapping(value = "sysUser")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @GetMapping(value = "getSysUserList")
    @RequiresPermissions(value = "cc")
    public Result getSysUserList(SysUser user)
    {
        List<SysUser> userList = sysUserService.getSysUserList(user);
        return Result.success(userList);
    }

    @GetMapping(value = "getUserRoleMenuInfo")
    @RequiresRoles(value = "user")
    public Result getUserRoleMenuInfo(String userName)
    {
        UserRoleMenu urm = sysUserService.getUserRoleMenuInfo(userName);
        return new Result(200,"操作成功",JSONObject.toJSONString(urm));
    }

    @PostMapping(value = "insertSysUser")
    @NotNull(param = "sysUserName")
    public Result insertSysUser(SysUser sysUser)
    {
        if (sysUser != null)
        {
            sysUser.setUserId(StringUtils.getId());
            sysUser.setCreateTime(new Date());
            sysUser.setUpdateTime(new Date());
            try {
                int res = sysUserService.insertSysUser(sysUser);
                return Result.success();
            }catch (Exception e)
            {
                e.printStackTrace();
                return Result.fial("操作失败");
            }
        }
        return Result.fial(501,"请输入参数");
    }

}
