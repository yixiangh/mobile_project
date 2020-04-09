package com.example.mobile.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

/**
 * 定义统一返回格式
 * @param
 */
@Data
public class Result {

    /**
     * 返回码
     */
    private int code;
    /**
     * 返回信息
     */
    private String msg;
    /**
     * 返回数据
     */
    private Object obj;

    public Result(int code,Object obj,String msg)
    {
        this.code = code;
        this.obj = obj;
        this.msg = msg;
    }

    public static Result success(Object obj)
    {
        return new Result(200, obj,"操作成功");
    }
    public static Result success()
    {
        return new Result(200,null,"操作成功");
    }
    public static Result fial(int code,String msg){
        return new Result(code,null,msg);
    }
    public static Result fial(int code)
    {
        String msg = PropertiesUtil.getString(code);
        return new Result(code,null,msg);
    }
    public static Result fial(String msg){
        return new Result(500,null,msg);
    }


}
