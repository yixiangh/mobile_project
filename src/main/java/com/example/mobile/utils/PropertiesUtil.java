package com.example.mobile.utils;

import org.springframework.core.env.Environment;

/**
 * 定义配置文件读取类（util）
 * 使用Environment
 */
public class PropertiesUtil {

    private static Environment env;

    /**
     * 供PropertiesConfig对Environment进行初始化
     * @param env
     */
    public static void setEnv(Environment env)
    {
        PropertiesUtil.env = env;
    }

    /**
     * 根据key获取配置文件中对应的值
     * @param key
     * @return
     */
    public static String getString(String key)
    {
        return env.getProperty(key);
    }
    public static String getString(int key)
    {
        return env.getProperty(String.valueOf(key));
    }

    public static int getInt(String key)
    {
        return Integer.valueOf(env.getProperty(key));
    }
    public static int getInt(int key)
    {
        return Integer.valueOf(env.getProperty(String.valueOf(key)));
    }


}
