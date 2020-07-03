package com.example.mobile.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.mobile.base.exception.SystemException;
import com.example.mobile.constance.JwtConstant;
import com.example.mobile.model.entity.SysUser;
import com.example.mobile.service.SysUserService;
import com.example.mobile.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 登录控制
 */
@RestController
@RequestMapping(value = "/")
@Slf4j
public class LoginController {

    @Autowired
    private SysUserService userService;

    @Autowired
    private JedisUtils jedisUtils;
    @Value("${checkCode.expire.time}")
    private Integer checkCodeExpireTime;

    /**
     * 执行登录
     * @return
     */
    @PostMapping(value = "login")
    public Result login(String userName, String password, String picLyanzhengma) {
        if (StringUtils.isEmpty(userName)) {
            throw new SystemException(1001);
        }
        if (StringUtils.isEmpty(password)) {
            throw new SystemException(1002);
        }
//        if (StringUtils.isEmpty(picLyanzhengma))
//        {
//            throw new SystemException(1014);
//        }
//        if (jedisUtils.existKey(picLyanzhengma))
//        {
//            throw new SystemException(1015);
//        }
        SysUser sysUser = new SysUser();
        sysUser.setUserName(userName);
        sysUser.setUserPassword(password);
        SysUser loginUser = userService.getLoginUserInfo(sysUser);
        //创建JWTToken并返回
        MyClaim claims = new MyClaim();
        claims.setUserId(loginUser.getUserId());
        claims.setUserName(loginUser.getUserName());
        String token = JwtUtil.encodeToken(claims);
        jedisUtils.setString(JwtConstant.PREFIX_USER_TOKEN + token, token, JwtConstant.EXPIRE_TIME);
        return Result.success(token);

    }

    /**
     * 获取验证码
     * @return
     */
    @GetMapping(value = "getCheckCode")
    public void getCheckCode(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        VerificationCodeUtils verificationCode = new VerificationCodeUtils();
        verificationCode.getRandcode(request, response, checkCodeExpireTime, jedisUtils);//输出验证码图片
    }

    /**
     * 退出登录
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/logout")
    public Result logout(HttpServletRequest request, HttpServletResponse response) {
        //用户退出逻辑
        String token = request.getHeader(JwtConstant.JWT_TOKEN);
        if (StringUtils.isEmpty(token)) {
            return Result.fial(1011);
        }
        String userStr = JwtUtil.decodeToken(token);
        JSONObject userJson = JSONObject.parseObject(userStr);
        String userName = userJson.getString("userName");
        SysUser sysUser = new SysUser();
        sysUser.setUserName(userName);
        SysUser user = userService.getLoginUserInfo(sysUser);
        if (user != null) {
            log.info(" 用户名:  " + sysUser.getUserRealName() + ",退出成功！ ");
            //清空用户Token缓存
            jedisUtils.del(JwtConstant.PREFIX_USER_TOKEN + token);
            //清空用户权限缓存：权限Perms和角色集合
//            jedisUtils.del(JwtConstant.LOGIN_USER_CACHERULES_ROLE + username);
//            jedisUtils.del(JwtConstant.LOGIN_USER_CACHERULES_PERMISSION + username);
            return Result.success();
        } else {
            return Result.fial(1003);
        }
    }


}
