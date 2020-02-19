package com.primeton.liuzhichao.demo.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;



@RestController
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
//	@MessageMapping("/ws/chat")  //作用类似于RequestMapping
//	public void handleChat(Principal principal,String msg) {
//		//获取要发送的目标用户
//		String destUser = msg.substring(msg.lastIndexOf(";")+1, msg.length());
//		//获取消息
//		String message = msg.substring(0, msg.lastIndexOf(";"));
//		simpMessagingTemplate.convertAndSendToUser(destUser, "/queue/chat",new ChatResp(message,principal.getName()));
//	}
	
}
