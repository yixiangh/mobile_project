package com.example.mobile.model.vo;

import com.example.mobile.model.entity.MenuInfo;
import lombok.Data;

import javax.management.relation.RoleInfo;
import java.util.List;
import java.util.Set;

/**
 * 用户角色权限信息
 */
@Data
public class UserRoleMenu {

    private Long userId;
    private String userName;
    private String userRealName;
    private Set<String> roles;
    private Set<String> menus;
    private String isDel;
}
