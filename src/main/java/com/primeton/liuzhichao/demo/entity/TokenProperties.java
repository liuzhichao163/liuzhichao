package com.primeton.liuzhichao.demo.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @ClassName TokenProperties
 * @Description TODO
 * @Author liuzhichao
 * @Date 2020/8/4 10:38
 * @Version 1.0
 */
@ConfigurationProperties(prefix = "token")
@Component
@Data
//@Configuration
public class TokenProperties {

//    @Value("${token.secretKey}")
    private String secretKey;
//    @Value("${token.tokenExpireSecond}")
    private int tokenExpireSecond;
//    @Value("${token.refreshTokenExpiredSecond}")
    private int refreshTokenExpiredSecond;
//    @Value("${token.authorizationHeaderName}")
    private String authorizationHeaderName;
//    @Value("${token.uniqueHeaderName}")
    private String uniqueHeaderName;
//    @Value("${token.role}")
    private String Role;



}
