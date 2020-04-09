package com.example.mobile.service;

import com.example.mobile.model.entity.SysUser;
import com.example.mobile.model.vo.UserRoleMenu;

import java.util.List;

public interface SysUserService {
    int insertSysUser(SysUser sysUser);

    List<SysUser> getSysUserList(SysUser user);

    SysUser getLoginUserInfo(SysUser user);

    UserRoleMenu getUserRoleMenuInfo(String userName);
}
