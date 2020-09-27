package com.primeton.liuzhichao.demo.utils;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import com.primeton.liuzhichao.demo.entity.TokenProperties;
import com.primeton.liuzhichao.demo.entity.User;
import com.primeton.liuzhichao.demo.redis.JedisClient;
import com.primeton.liuzhichao.demo.vo.UserVo;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultJws;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;


/**
 * iss: 该JWT的签发者
 * sub: 该JWT所面向的用户
 * aud: 接收该JWT的一方
 * exp(expires): 什么时候过期，这里是一个Unix时间戳
 * iat(issued at): 在什么时候签发的
 */
@Slf4j
@Component
public class JwtUtils {

    /**
     * 解析 jwt token
     *
     * @param token     需要解析的json
     * @param secretKey 密钥
     * @return
     */
    public static Jws<Claims> parserAuthenticateToken(String token, String secretKey) {
        try {
            final Jws<Claims> claimsJws = Jwts.parser()
                                            .setSigningKey(secretKey)
                                            .parseClaimsJws(token);
            return claimsJws;
        } catch (ExpiredJwtException e) {
            return new DefaultJws<>(null, e.getClaims(), "");
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException | IncorrectClaimException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 判断 jwt 是否过期
     *
     * @param
     * @return true:过期 false:没过期
     */
    public static boolean isJwtExpired(Claims claims) {

        return claims.getExpiration().before(new Date());
    }



    /**
     * 构建认证过的认证对象
     */
    public static Authentication buildAuthentication(Jws<Claims> jws, String userIdFieldName) {
        Object userId = jws.getBody().get(userIdFieldName);
        TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(userId, null, new ArrayList<>(0));
        SecurityContextHolder.getContext().setAuthentication(testingAuthenticationToken);
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 生成 jwt token
     */
    public static void generatorJwtToken(
                                        UserVo user,
                                        StringBuffer role,
                                        TokenProperties tokenProperties,
                                        HttpServletResponse response,
                                        JedisClient jedisClient ) {
        // 使用HS256加密算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        Date expireTime = Date.from(LocalDateTime.now().plusSeconds(tokenProperties.getTokenExpireSecond()).atZone(ZoneId.systemDefault()).toInstant());
        String jwt = Jwts.builder()  //这里其实就是new一个JwtBuilder，设置jwt的body
                .claim("user", user)//配置用户信息
                .claim("role", role)//配置用户角色
                .setSubject(user.getName())    //sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .setExpiration(expireTime)  //设置过期时间
                .signWith(signatureAlgorithm,tokenProperties.getSecretKey())  //设置签名使用的签名算法和签名使用的秘钥
                .compact();   //就开始压缩为xxxxxxxxxxxxxx.xxxxxxxxxxxxxxx.xxxxxxxxxxxxx这样的jwt

        //当生成或刷新token时，向redis中存入刷新token的时间间隔
        String key = user.getUserId() + "_token";
        String value = System.currentTimeMillis() + "";
        int time = tokenProperties.getRefreshTokenExpiredSecond();
        jedisClient.set(key,value,time);

        //将token放入response响应头中
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Authorization", jwt);
        httpServletResponse.setHeader("Access-Control-Expose-Headers", "Authorization");

    }

}
