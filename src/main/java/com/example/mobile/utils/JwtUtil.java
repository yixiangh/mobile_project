package com.example.mobile.utils;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Jwt生成/验证
 */
@Slf4j
public class JwtUtil {

    /**
     * 密钥盐
     */
    private static final String SECRET = "zkgjtdhsadk";
    /**
     * 该JWT的签发者
     */
    private static final String ISSUER = "auth001";
    /**
     * 过期时间为一天
     */
    private static final long EXPIRE_TIME = 1000*60*60*24;

    private static final String CLAIM_NAME="user";

    public static String encodeToken(MyClaim claims)
    {
        Date expiresAt = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Map<String,Object> header = new HashMap<>(2);
        header.put("typ","jwt");
        header.put("alg","HS256");
        JWTCreator.Builder builder = JWT.create();
        builder.withHeader(header);//设置JWT头部
        builder.withIssuer(ISSUER);//设置JWT签发人
        builder.withClaim(CLAIM_NAME, JSONObject.toJSONString(claims));//设置JWT载荷
        builder.withExpiresAt(expiresAt);//设置JWT过期时间
        String jwtToken = null;
        jwtToken = builder.sign(Algorithm.HMAC256(SECRET));
        return jwtToken;
    }

    /**
     * 验证token
     * @param token
     * @return
     */
    public static boolean verif(String token) {
        Algorithm algorithm =Algorithm.HMAC256(SECRET);
        JWTVerifier jwtVerifier = JWT.require(algorithm).withIssuer(ISSUER).build();
        try {
            DecodedJWT jwt = jwtVerifier.verify(token);
            log.info("认证通过：");
            log.info("issuer: " + jwt.getIssuer());
            log.info("user: " + jwt.getClaim("user").asString());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            log.info("过期时间：" + sdf.format(jwt.getExpiresAt()));
        }catch (Exception e)
        {
            e.printStackTrace();
            log.error("token验证失败，失败信息为："+e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 解析Token
     * @return
     */
    public static String decodeToken(String token)
    {
        DecodedJWT jwt = JWT.decode(token);
        Map<String,Claim> claimMap = jwt.getClaims();//获取JWT内容（header,payload,exp）
        Map<String,String> map = new HashMap<>();//定义map用于存储jwt解析出来的内容
        claimMap.forEach((k,v) -> map.put(k,v.asString()));
        return map.get(CLAIM_NAME);
    }

    public static void main(String[] args) throws InterruptedException {
        MyClaim user = new MyClaim();
        user.setUserId("123456");
        user.setUserName("admin");
        String token = encodeToken(user);
        System.out.println(token);
        String userstr = decodeToken(token);
        JSONObject json = JSONObject.parseObject(userstr);
        System.out.println(json.get("userName"));
//        MyClaim myClaim = userstr;
//        JSONObject userJson = JSONObject.parseObject();
//        System.out.println(userJson.getString("userName"));
    }

}
