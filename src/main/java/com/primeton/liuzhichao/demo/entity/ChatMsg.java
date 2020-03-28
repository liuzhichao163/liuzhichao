package com.primeton.liuzhichao.demo.entity;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ChatMsg {

	private String from;
	private String to;
	private String msg;
	private Date time;

	public ChatMsg() {
		super();
	}

	public ChatMsg(String from, String to, String msg, Date time) {
		super();
		this.from = from;
		this.to = to;
		this.msg = msg;
		this.time = time;
	}

}
