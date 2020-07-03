package com.example.mobile.service.impl;

import com.example.mobile.base.exception.SystemException;
import com.example.mobile.constance.Constant;
import com.example.mobile.mapper.MenuInfoMapper;
import com.example.mobile.mapper.SysRoleMapper;
import com.example.mobile.mapper.UserRoleRelationMapper;
import com.example.mobile.model.entity.MenuInfo;
import com.example.mobile.model.entity.SysRole;
import com.example.mobile.model.entity.SysUser;
import com.example.mobile.mapper.UserMapper;
import com.example.mobile.model.entity.UserRoleRelation;
import com.example.mobile.model.vo.Pager;
import com.example.mobile.model.vo.UserRoleMenu;
import com.example.mobile.service.SysUserService;
import com.example.mobile.utils.GenerateIdUtil;
import com.example.mobile.utils.StringUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SysRoleMapper roleMapper;
    @Autowired
    private MenuInfoMapper menuMapper;
    @Autowired
    private UserRoleRelationMapper userRoleRelationMapper;

    @Override
    public int insertSysUser(SysUser sysUser) {
        return userMapper.insertSysUser(sysUser);
    }

    @Override
    public Pager getSysUserList(SysUser user) {
        user.setIsDel(Constant.IS_DEL_FALSE);
        PageHelper.startPage(user.getPageNum(), user.getPageSize());
        Page<SysUser> userList = userMapper.getSysUserList(user);
        Pager pager = new Pager();
        pager.setCount(userList.getTotal());
        pager.setData(userList);
        return pager;
    }

    @Override
    public SysUser getLoginUserInfo(SysUser user) {
        user.setIsDel(Constant.IS_DEL_FALSE);
        SysUser loginUser = userMapper.getLoginUserInfo(user);
        if (loginUser == null) {
            throw new SystemException(1005);//用户不存在
        }
        if (loginUser.getUserStatus() == Constant.USER_STATUS_LOCKING) {
            throw new SystemException(1006);//用户已被锁定
        }
        return loginUser;
    }

    @Override
    public UserRoleMenu getUserRoleMenuInfo(String userName) {
        UserRoleMenu urm = new UserRoleMenu();
        urm.setUserName(userName);
        urm.setIsDel(Constant.IS_DEL_FALSE);
        SysUser user = userMapper.getLoginUserInfoByUrm(urm);
        if (user != null) {
            urm.setUserId(user.getUserId());
            urm.setUserName(user.getUserName());
            urm.setUserRealName(user.getUserRealName());
            List<SysRole> roleList = roleMapper.getRolesByUrm(urm);//获取角色信息
            if (roleList != null && roleList.size() > 0) {
                urm.setRoles(listToSet(roleList, null));
                List<MenuInfo> menuList = menuMapper.getMenusByRoles(roleList, Constant.IS_DEL_FALSE);//获取菜单信息
                urm.setMenus(listToSet(null, menuList));
            }
        }
        return urm;
    }

    @Override
    public int editUser(SysUser user) {
        try {
            if (StringUtils.isEmpty(user.getUserId())) {
                throw new SystemException(1005);
            }
            return userMapper.editUser(user);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new SystemException(1011);
        }

    }

    @Override
    public int delUser(SysUser user) {
        try {
            if (StringUtils.isEmpty(user.getUserId())) {
                throw new SystemException(1005);
            }
            user.setIsDel(Constant.IS_DEL_TRUE);
            return userMapper.delUser(user);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new SystemException(1011);
        }
    }

    @Override
    public int addUserRole(UserRoleRelation userRole) {
        userRole.setUserRoleId(new GenerateIdUtil(1l, 1l).nextId());
        userRole.setCreateTime(new Date());
        userRole.setUpdateTime(new Date());
        return userRoleRelationMapper.insert(userRole);
    }

    @Override
    public int editUserRole(UserRoleRelation userRole) {
        userRole.setUpdateTime(new Date());
        return userRoleRelationMapper.edit(userRole);
    }

    @Override
    public int delUserRole(UserRoleRelation userRole) {
        return userRoleRelationMapper.deleteByPrimaryKey(userRole.getUserRoleId());
    }


    public Set<String> listToSet(List<SysRole> roleList, List<MenuInfo> menuList) {
        Set<String> res = new HashSet<>();
        if (roleList != null && roleList.size() > 0) {
            for (int i = 0; i < roleList.size(); i++) {
                res.add(roleList.get(i).getRoleCode());
            }
        }
        if (menuList != null && menuList.size() > 0) {
            for (int i = 0; i < menuList.size(); i++) {
                res.add(menuList.get(i).getMenuCode());
            }
        }
        return res;
    }


}
