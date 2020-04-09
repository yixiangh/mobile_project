package com.example.mobile.service.impl;

import com.example.mobile.base.constant.Constant;
import com.example.mobile.mapper.MenuInfoMapper;
import com.example.mobile.mapper.SysRoleMapper;
import com.example.mobile.model.entity.MenuInfo;
import com.example.mobile.model.entity.SysRole;
import com.example.mobile.model.entity.SysUser;
import com.example.mobile.mapper.UserMapper;
import com.example.mobile.model.vo.UserRoleMenu;
import com.example.mobile.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SysRoleMapper roleMapper;
    @Autowired
    private MenuInfoMapper menuMapper;

    @Override
    public int insertSysUser(SysUser sysUser) {
        return userMapper.insertSysUser(sysUser);
    }

    @Override
    public List<SysUser> getSysUserList(SysUser user) {
        return userMapper.getSysUserList(user);
    }

    @Override
    public SysUser getLoginUserInfo(SysUser user) {
        user.setIsDel(Constant.IS_DEL_FALSE);
        return userMapper.getLoginUserInfo(user);
    }

    @Override
    public UserRoleMenu getUserRoleMenuInfo(String userName) {
        UserRoleMenu urm = new UserRoleMenu();
        urm.setUserName(userName);
        urm.setIsDel(Constant.IS_DEL_FALSE);
        SysUser user = userMapper.getLoginUserInfoByUrm(urm);
        if (user != null)
        {
            urm.setUserId(user.getUserId());
            urm.setUserName(user.getUserName());
            urm.setUserRealName(user.getUserRealName());
            List<SysRole> roleList = roleMapper.getRolesByUrm(urm);//获取角色信息
            if (roleList != null && roleList.size()>0)
            {
                urm.setRoles(listToSet(roleList,null));
                List<MenuInfo> menuList = menuMapper.getMenusByRoles(roleList,Constant.IS_DEL_FALSE);//获取菜单信息
                urm.setMenus(listToSet(null,menuList));
            }
        }
        return urm;
    }

    public Set<String> listToSet(List<SysRole> roleList,List<MenuInfo> menuList)
    {
        Set<String> res = new HashSet<>();
        if (roleList != null && roleList.size() > 0)
        {
            for (int i = 0; i < roleList.size(); i++) {
                res.add(roleList.get(i).getRoleCode());
            }
        }
        if (menuList != null && menuList.size() > 0)
        {
            for (int i = 0; i < menuList.size(); i++) {
                res.add(menuList.get(i).getMenuCode());
            }
        }
        return res;
    }





}
