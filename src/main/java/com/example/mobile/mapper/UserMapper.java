package com.example.mobile.mapper;

import com.example.mobile.model.entity.SysUser;
import com.example.mobile.model.vo.UserRoleMenu;
import com.github.pagehelper.Page;

import java.util.List;

public interface UserMapper {

    int insertSysUser(SysUser sysUser);

    Page<SysUser> getSysUserList(SysUser user);

    SysUser getLoginUserInfo(SysUser user);

    UserRoleMenu getUserRoleMenuInfo(SysUser user);

    SysUser getLoginUserInfoByUrm(UserRoleMenu urm);

    int editUser(SysUser user);

    int delUser(SysUser user);

}
