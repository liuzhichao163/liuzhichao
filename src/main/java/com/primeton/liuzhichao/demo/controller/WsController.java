package com.primeton.liuzhichao.demo.controller;



import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.primeton.liuzhichao.demo.entity.ChatMsg;
import com.primeton.liuzhichao.demo.entity.ChatResp;



@Controller
public class WsController {
	
	//Spring-WebSocket内置的一个消息发送工具,可以将消息发送到指定的客户端
	@Autowired
    SimpMessagingTemplate simpMessagingTemplate;
	
	
		@MessageMapping("/ws/nf")
	    @SendTo("/topic/nf")  
	    public String handleNF() throws InterruptedException {
			System.out.println("=========系统消息===========");
	        return "系统消息";
	    }
	
	/**
	 * 
	 * @param principal  spring mvc中，principal包含了登录用户的信息，
	 * @param msg
	 */
	@MessageMapping("/ws/chat")  //作用类似于RequestMapping
	public void handleChat(ChatMsg chatMsg) {
		chatMsg.setTime(new Date());
		System.out.println("======chatMsg====="+chatMsg.getTo()+"---"+chatMsg.getMsg()+"---"+chatMsg.getFrom());
		simpMessagingTemplate.convertAndSendToUser(chatMsg.getTo(), "/queue/chat", chatMsg);
	}
	
}
