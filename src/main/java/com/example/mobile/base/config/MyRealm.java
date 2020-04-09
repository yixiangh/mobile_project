package com.example.mobile.base.config;

import com.alibaba.fastjson.JSONObject;
import com.example.mobile.base.constant.Constant;
import com.example.mobile.base.exception.SystemException;
import com.example.mobile.model.entity.SysUser;
import com.example.mobile.model.vo.UserRoleMenu;
import com.example.mobile.service.SysUserService;
import com.example.mobile.utils.JwtToken;
import com.example.mobile.utils.JwtUtil;
import com.example.mobile.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义Realm设置鉴权（是否登录）/授权（是否拥有权限）
 * Shiro中，最终是通过 Realm 来获取应用程序中的用户、角色及权限信息的。
 * 使用@Component注解将自定义的MyRealm类交给Spring管理，方便后边注入到SecurityManager
 */
@Slf4j
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService userService;

    /**
     * 必须重写此方法，不然会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 获取身份验证信息
     * @param authenticationToken 用户身份信息 token
     * @return 返回封装了用户信息的 AuthenticationInfo 实例
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("开始验证身份信息");
        String token = (String) authenticationToken.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String userStr = JwtUtil.decodeToken(token);
        if (StringUtils.isEmpty(userStr))
        {
            throw new SystemException(1003);//token无效
        }
        JSONObject userJson = JSONObject.parseObject(userStr);
        String userName = userJson.getString("userName");
        SysUser sysUser = new SysUser();
        sysUser.setUserName(userName);
        SysUser user = userService.getLoginUserInfo(sysUser);
        if (user == null) {
            throw new UnknownAccountException("账户不存在！");
//            throw new SystemException(1005);
        }
        if (user.getUserStatus() == Constant.USER_STATUS_LOCKING)
        {
            throw new DisabledAccountException("账户已锁定！");
//            throw new SystemException(1006);
        }
        return new SimpleAuthenticationInfo(token,token,getName());
    }

    /**
     * 获取授权信息
     * @param principalCollection
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("开始执行权限认证");
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        String userStr = JwtUtil.decodeToken(token);
        JSONObject userJson = JSONObject.parseObject(userStr);
        String userName = userJson.getString("userName");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获得该用户角色
        UserRoleMenu urm = userService.getUserRoleMenuInfo(userName);

//        Set<String> roles = new HashSet<>();//定义当前用户拥有角色集合，并提交给Shiro的Realm
//        Set<String> permissions = new HashSet<>();//定义当前用户拥有权限集合，并提交给Shiro的Realm
//        roles.add("user");
//        permissions.add("add");
        //设置该用户拥有的角色
        info.setRoles(urm.getRoles());
        info.setStringPermissions(urm.getMenus());
        return info;
    }




}
