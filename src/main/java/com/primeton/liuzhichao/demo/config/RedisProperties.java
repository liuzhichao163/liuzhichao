package com.primeton.liuzhichao.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 读取配置文件中的redis的配置，进行封装
 * @ConfigurationProperties(prefix = "spring.redis") 
 * 从配置文件中获取spring.redis开头的配置数据并绑定到属性
 * @author ASUS
 *
 */
@Configuration
@ConfigurationProperties(prefix = "spring.redis")  
public class RedisProperties {
	
	private String host;
    private String port;
    
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
    
    

}
