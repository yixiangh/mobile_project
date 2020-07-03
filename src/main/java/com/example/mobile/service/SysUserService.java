package com.example.mobile.service;

import com.example.mobile.model.entity.SysUser;
import com.example.mobile.model.entity.UserRoleRelation;
import com.example.mobile.model.vo.Pager;
import com.example.mobile.model.vo.UserRoleMenu;


public interface SysUserService {
    int insertSysUser(SysUser sysUser);

    Pager<SysUser> getSysUserList(SysUser user);

    SysUser getLoginUserInfo(SysUser user);

    UserRoleMenu getUserRoleMenuInfo(String userName);

    int editUser(SysUser user);

    int delUser(SysUser user);

    int addUserRole(UserRoleRelation userRole);

    int editUserRole(UserRoleRelation userRole);

    int delUserRole(UserRoleRelation userRole);
}
