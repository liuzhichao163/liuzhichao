package com.primeton.liuzhichao.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
/**
 * webSocket配置类，定义全局配置信息
 * @author ASUS
 *
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{
	
	/**
	 * 添加一个服务端点，来接受客户端的连接
	 * registry.addEndpoint("/ws/endpointChat") 表示添加了一个"/ws/endpointChat"的端点，
	 * 客户端可以通过这个端点来连接服务端。
	 * withSockJS()  开启sockJS支持。
	 * @param registry
	 */
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/webSocket") .setAllowedOrigins("*").withSockJS();	
	}
	
	/**
	 *定义消息代理，设置消息连接时的各种规范信息。
	 * 
	 *registry.enableSimpleBroker("/queue","/topic");
	 *表示客户端订阅地址的前缀信息，也就是客户端接收服务端消息的地址的前缀信息
	 */
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/topic");
		//设置客户端前缀 即@MessageMapping
		registry.setApplicationDestinationPrefixes("/app");
		//点对点发送前缀
		registry.setUserDestinationPrefix("/user");
	}
	
	
}
