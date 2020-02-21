package com.primeton.liuzhichao.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 将配置文件中的数据，转换成程序可用的jedis客户端
 * 
 * @author ASUS
 *
 */
@Configuration
public class RedisConfig {

	@Autowired
	private RedisProperties redisProperties;

	@Bean
	public JedisPool jedisPool() {
		
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxIdle(redisProperties.getMaxIdle());
		jedisPoolConfig.setMaxWaitMillis(redisProperties.getMaxWaitMillis());
		jedisPoolConfig.setMaxTotal(redisProperties.getMaxActive());
		jedisPoolConfig.setMinIdle(redisProperties.getMinIdle());
		String host = redisProperties.getHost();
		Integer port = Integer.parseInt(redisProperties.getPort());
		Integer timeout = redisProperties.getTimeout();
		
		return new JedisPool(jedisPoolConfig,host,port,timeout,null);
	}

	public static String getPackages() {
		return "niaho";
	}

}
