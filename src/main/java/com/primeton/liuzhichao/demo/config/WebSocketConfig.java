package com.primeton.liuzhichao.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
/**
 * webSocket配置类，定义全局配置信息
 * @author ASUS
 *@EnableWebSocketMessageBroker注解表明： 这个配置类不仅配置了 WebSocket，还配置了基于代理的 STOMP消息； 
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{
	
	/**
	 * 添加一个服务端点，来接受客户端的连接
	 * registry.addEndpoint("/webSocket") 表示添加了一个"/webSocket"的端点，
	 * 客户端可以通过这个端点来连接服务端。
	 * 这个路径与发送和接收消息的目的路径有所不同， 这是一个端点，客户端在订阅或发布消息到目的地址前，要连接该端点。
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
		//客户端订阅服务端消息的地址前缀信息
		registry.enableSimpleBroker("/queue","/topic");
		//设置客户端访问前缀 即@MessageMapping；例如前端send的地址为"/app/ws/nf",中的/app在此处设置
		//定义了服务端接收地址的前缀，也即客户端给服务端发消息的地址前缀  
		registry.setApplicationDestinationPrefixes("/app");
		//点对点发送前缀,"/user/queue/chat"
		//点对点使用的订阅前缀（客户端订阅路径上会体现出来），不设置的话，默认也是/user/ 
		registry.setUserDestinationPrefix("/user");
	}
}
