package com.example.mobile.model.vo;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

import java.util.List;

/**
 * 菜单树
 */
@Data
public class MenuTree {

    /**
     * 菜单主键id
     */
    private Long id;
    /**
     * 父级id
     */
    private Long pid;
    /**
     * 菜单名称
     */
    private String label;
    /**
     * 子级菜单
     */
    private List<MenuTree> children;
}
