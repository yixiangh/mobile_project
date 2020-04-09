package com.example.mobile.base.config;

import com.alibaba.fastjson.JSONObject;
import com.example.mobile.utils.HttpClientUtils;
import com.example.mobile.utils.JedisUtils;
import com.example.mobile.utils.StringUtils;

/**
 * 微信配置
 */
public class WechatConfig {

    public static final String appid = "wx1cb8466b6403ed5e";
    public static final String appsecret = "222d60c03a1a1f552015f9fd00ce2580";
    public static final String accessTokenName = "accessToken";

    public static void main(String[] args) throws Exception {
        String accessToken = "31_L-li5bddZ-kq3iy-HjPPaVcQbVvDoH96MQYC2TQPvyYyZq6zdiJEda9EXmM2pGdDkk19NcHcSnl_GS84CKuHUCsUuC-rsqAUYIcMUzF_YsD0cK0zgI2XYO_oXvYtfD6LwV3lP7hLDPOaqjVjZZGiADAEEL";
        String res = getWechatUser(accessToken);
        System.out.println(res);
    }
    /**
     * 获取access_token
     * @return
     * @throws Exception
     */
    public static String getAccessToken() throws Exception {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+appsecret;
        String res = HttpClientUtils.httpGet(url);
        JSONObject jsonObject = new JSONObject();
        String access_token = null;
        if (StringUtils.isNotEmpty(res))
        {
            jsonObject = JSONObject.parseObject(res);
            access_token = jsonObject.getString("access_token");
            int expiresIn = jsonObject.getIntValue("expires_in");//有效时间，单位/秒
            new JedisUtils().setString(accessTokenName,access_token,expiresIn);//存入redis

        }
        return access_token;
    }

    /**
     * 获取微信用户信息
     * @return
     */
    public static String getWechatUser(String accessToken) throws Exception {
        String url = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token="+accessToken;
        String json = null;
        String res = HttpClientUtils.httpsPost(url,json);

        return res;
    }



}
