package com.primeton.liuzhichao.demo.entity;

import java.util.Date;

import lombok.Data;
import lombok.ToString;
/**
 * 系统消息的实体类
 * @author ASUS
 *
 */
@Data
@ToString
public class MsgContent {
	private Integer id;
	private String title;
	private String content;
	private Date date;
	
	public MsgContent() {
		super();
	}

	public MsgContent(Integer id, String title, String content, Date date) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.date = date;
	}
	
	
	


}
