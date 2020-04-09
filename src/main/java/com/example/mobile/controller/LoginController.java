package com.example.mobile.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.mobile.base.exception.SystemException;
import com.example.mobile.model.entity.SysUser;
import com.example.mobile.service.SysUserService;
import com.example.mobile.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 登录控制
 */
@RestController
@RequestMapping(value = "/")
public class LoginController {

    @Autowired
    private SysUserService userService;

    @Autowired
    private JedisUtils jedisUtils;

    /**
     * 执行登录
     * @return
     */
    @PostMapping(value = "login")
    public Result login(@Valid String userName, @Valid String userPassword)
    {
        if (StringUtils.isEmpty(userName))
        {
            throw new SystemException(1001);
        }
        if (StringUtils.isEmpty(userPassword))
        {
            throw new SystemException(1002);
        }
        SysUser sysUser = new SysUser();
        sysUser.setUserName(userName);
        sysUser.setUserPassword(userPassword);
        SysUser loginUser = userService.getLoginUserInfo(sysUser);
        String token = null;
        if (loginUser == null)
        {
            throw new SystemException(1006);
        }
        jedisUtils.setString("sysUser", JSONObject.toJSONString(loginUser));
        MyClaim claims = new MyClaim();
        claims.setUserId(loginUser.getUserId());
        claims.setUserName(loginUser.getUserName());
        token = JwtUtil.encodeToken(claims);
        return Result.success(token);

    }

    @GetMapping(value = "notLogin")
    public Result noLogin()
    {
        return Result.fial(1007);
    }


}
