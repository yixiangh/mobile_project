package com.example.mobile.model.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.example.mobile.model.vo.Pager;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 系统用户
 */
@Data
public class SysUser extends Pager implements Serializable {

    /**
     * 主键id
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long userId;
    /**
     * 系统用户登录账户
     */
    @NotNull(message = "用户名不能为空")
    private String userName;
    /**
     * 系统用户登录密码
     */
    @NotEmpty(message = "密码不能为空")
    private String userPassword;
    /**
     * 用户真是姓名
     */
    private String userRealName;
    /**
     * 年龄
     */
    private int userAge;
    /**
     * 性别(1:男，2：女)
     */
    private int userSex;
    /**
     * 手机号
     */
    private String userPhone;
    /**
     * 用户状态(1：正常，2：已锁定）
     */
    private int userStatus;
    /**
     * 逻辑删除（N：正常，Y：已删除）
     */
    private String isDel;
    private Date updateTime;
    private Date createTime;

}
