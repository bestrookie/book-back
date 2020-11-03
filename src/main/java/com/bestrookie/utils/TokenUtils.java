package com.bestrookie.utils;

/**
 * @author : bestrookie
 * @date : 15:24 2020/9/24
 */
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * @desc   使用token验证用户是否登录
 * @author bestrookie
 **/
@Slf4j
public class TokenUtils {
    /**
     * 设置过期时间
     */
    private static final long EXPIRE_DATE=24*60*60*1000;
    /**
     * token密钥
     */
    private static final String TOKEN_SECRET = "xiaofeng";

    public static String token (String phone,int userId,int userRole){

        String token = "";
        try {
            //过期时间
            Date date = new Date(System.currentTimeMillis()+EXPIRE_DATE);
            //秘钥及加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            //设置头部信息
            Map<String,Object> header = new HashMap<>();
            header.put("typ","JWT");
            header.put("alg","HS256");
            //携带username，password信息，生成签名
            token = JWT.create()
                    .withHeader(header)
                    .withClaim("userId",userId)
                    .withClaim("role",userRole)
                    .withClaim("userName",phone).withExpiresAt(date)
                    .sign(algorithm);
        }catch (Exception e){
            log.info("token过期或错误");
            return  null;
        }
        return token;
    }

    public static boolean verify(String token){
        /**
         * @desc   验证token，通过返回true
         * @params [token]需要校验的串
         **/
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        }catch (Exception e){

            log.info("token过期或错误");
            return  false;
        }
    }

    public static String getInfo(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim("userName").asString();
        }catch (Exception e){

            log.info("token过期或错误");
            return null;
        }

    }
    public static int getId(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim("userId").asInt();
        }catch (Exception e){
            log.info("token过期或错误");
            return 0;
        }
    }
    public static int getRole(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim("role").asInt();
        }catch (Exception e){
            log.info("token过期或错误");
            return 0;
        }
    }
}