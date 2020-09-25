package com.primeton.liuzhichao.demo.entity;

import java.io.Serializable;

/**
 * 消息实体类
 * msg：消息内容
 * from：消息发送人
 * @author ASUS
 *
 */
public class ChatResp implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5090471725636392030L;

	private String msg;
	
	private String from;
	
	public ChatResp() {
		super();
	}
	
	public ChatResp(String msg, String from) {
		super();
		this.msg = msg;
		this.from = from;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((msg == null) ? 0 : msg.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChatResp other = (ChatResp) obj;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (msg == null) {
			if (other.msg != null)
				return false;
		} else if (!msg.equals(other.msg))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ChatResp [msg=" + msg + ", from=" + from + "]";
	}
	
	
	
}
