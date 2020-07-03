package com.example.mobile.base.config;

import com.alibaba.fastjson.JSONObject;
import com.example.mobile.base.exception.SystemException;
import com.example.mobile.constance.Constant;
import com.example.mobile.constance.JwtConstant;
import com.example.mobile.model.entity.SysUser;
import com.example.mobile.model.vo.UserRoleMenu;
import com.example.mobile.service.SysUserService;
import com.example.mobile.utils.*;
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

    @Autowired
    private JedisUtils jedisUtils;

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
        if (StringUtils.isEmpty(userStr)) {
            throw new SystemException(1003);//token无效
        }
        JSONObject userJson = JSONObject.parseObject(userStr);
        String userName = userJson.getString("userName");
        if (!jwtTokenRefresh(token, userName)) {
            throw new SystemException(1004);//token已过期
        }
        SysUser sysUser = new SysUser();
        sysUser.setUserName(userName);
        SysUser user = userService.getLoginUserInfo(sysUser);
        if (user == null) {
            throw new UnknownAccountException("账户不存在！");
        }
        if (user.getUserStatus() == Constant.USER_STATUS_LOCKING) {
            throw new DisabledAccountException("账户已锁定！");
        }
        return new SimpleAuthenticationInfo(token, token, getName());
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

    /**
     * JWTToken刷新生命周期 （解决用户一直在线操作，提供Token失效问题）
     * 1、登录成功后将用户的JWT生成的Token作为k、v存储到cache缓存里面(这时候k、v值一样)
     * 2、当该用户再次请求时，通过JWTFilter层层校验之后会进入到doGetAuthenticationInfo进行身份验证
     * 3、当该用户这次请求JWTToken值还在生命周期内，则会通过重新PUT的方式k、v都为Token值，缓存中的token值生命周期时间重新计算(这时候k、v值一样)
     * 4、当该用户这次请求jwt生成的token值已经超时，但该token对应cache中的k还是存在，则表示该用户一直在操作只是JWT的token失效了，程序会给token对应的k映射的v值重新生成JWTToken并覆盖v值，该缓存生命周期重新计算
     * 5、当该用户这次请求jwt在生成的token值已经超时，并在cache中不存在对应的k，则表示该用户账户空闲超时，返回用户信息已失效，请重新登录。
     * 6、每次当返回为true情况下，都会给Response的Header中设置Authorization，该Authorization映射的v为cache对应的v值。
     * 7、注：当前端接收到Response的Header中的Authorization值会存储起来，作为以后请求token使用
     * 参考方案：https://blog.csdn.net/qq394829044/article/details/82763936
     *
     * @param userName
     * @return
     */
    public boolean jwtTokenRefresh(String token, String userName) {
        String cacheToken = jedisUtils.getString(JwtConstant.PREFIX_USER_TOKEN + token);
        if (StringUtils.isNotEmpty(cacheToken)) {
            // 校验token有效性
//            if (!JwtUtil.verif(cacheToken)) {
//                MyClaim claim = new MyClaim();
//                claim.setUserName(userName);
//                String newAuthorization = JwtUtil.encodeToken(claim);
//                jedisUtils.setString(JwtConstant.PREFIX_USER_TOKEN + token, newAuthorization,JwtConstant.EXPIRE_TIME);
//            } else {
            jedisUtils.setString(JwtConstant.PREFIX_USER_TOKEN + token, cacheToken, JwtConstant.EXPIRE_TIME);
//            }
            return true;
        }
        return false;
    }


}
