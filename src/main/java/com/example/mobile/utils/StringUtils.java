package com.example.mobile.utils;

import java.util.UUID;

/**
 * 字符串工具类
 */
public class StringUtils {

    /**
     * 获取主键
     * * 1秒=1000毫秒(ms)
        1毫秒=1／1,000秒(s)
        1秒=1,000,000 微秒(μs)
        1微秒=1／1,000,000秒(s)
        1秒=1,000,000,000 纳秒(ns)
        1纳秒=1／1,000,000,000秒(s)
     * @return
     */
    public static String getId(){
        String reandomStr = UUID.randomUUID().toString().replace("-","");
        String mili = System.nanoTime()+"";  //纳秒
        mili = mili.substring(mili.length()-6,mili.length());
        return reandomStr+mili;
    }

    /**
     * 字符串是否为空
     * @param val 字符串
     * @return boolean
     */
    public static boolean isEmpty(String val){
        if(val==null||val.length()==0||"null".equalsIgnoreCase(val)){
            return true;
        }else{
            return false;
        }
    }
    /**
     * 字符串是否为空
     * @param val 字符串
     * @return boolean
     */
    public static boolean isNotEmpty(String val){
        if(val==null||val.length()==0||"null".equalsIgnoreCase(val)){
            return false;
        }else{
            return true;
        }
    }

}
