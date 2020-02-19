package com.primeton.liuzhichao.demo.entity;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SysMsg implements Serializable{

	private static final long serialVersionUID = 740012979491927868L;
	
	private Integer id;
	private Integer mid;
	private Integer type;
	private Integer uid;
	private Integer state;
	private MsgContent msgContent;
	
	public SysMsg() {
		super();
	}
	
	public SysMsg(Integer id, Integer mid, Integer type, Integer uid, Integer state, MsgContent msgContent) {
		super();
		this.id = id;
		this.mid = mid;
		this.type = type;
		this.uid = uid;
		this.state = state;
		this.msgContent = msgContent;
	}

}
