package com.primeton.liuzhichao.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.JedisPool;

/**
 * 将配置文件中的数据，转换成程序可用的jedis客户端
 * @author ASUS
 *
 */
@Configuration
public class RedisConfig {
	
	@Autowired
    private RedisProperties redisProperties;
	
	@Bean
	public JedisPool jedisPool(){
		 String host = redisProperties.getHost();
	     Integer port = Integer.parseInt(redisProperties.getPort());
	     return new JedisPool(host,port);
	 }
	
	public static String getPackages(){
        return "niaho";
    }
	
}
