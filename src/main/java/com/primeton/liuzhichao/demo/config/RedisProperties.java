package com.primeton.liuzhichao.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 读取配置文件中的redis的配置，进行封装
 * @ConfigurationProperties(prefix = "spring.redis") 
 * 从配置文件中获取spring.redis开头的配置数据并绑定到属性
 * @author ASUS
 *
 */
@Configuration
//@ConfigurationProperties(prefix = "spring.redis")  
public class RedisProperties {
	
	@Value("${spring.redis.host}")
	private String host;
	
	@Value("${spring.redis.port}")
    private String port;
	
	@Value("${spring.redis.jedis.pool.max-active}")
    private int maxActive;
	
	@Value("${spring.redis.jedis.pool.max-wait}")
    private int maxWaitMillis;
	
	@Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;
	
	@Value("${spring.redis.jedis.pool.min-idle}")
    private int minIdle;
	
	@Value("${spring.redis.timeout}")
    private int timeout;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public int getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public int getMaxWaitMillis() {
		return maxWaitMillis;
	}

	public void setMaxWaitMillis(int maxWaitMillis) {
		this.maxWaitMillis = maxWaitMillis;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public int getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
    
	

}
