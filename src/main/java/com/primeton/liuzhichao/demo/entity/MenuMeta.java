package com.primeton.liuzhichao.demo.entity;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MenuMeta implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7253348330244708561L;
	
	private Boolean keepAlive;
	private Boolean requireAuth;
	
	
	public MenuMeta() {
		super();
	}
	
	
	public MenuMeta(Boolean keepAlive, Boolean requireAuth) {
		super();
		this.keepAlive = keepAlive;
		this.requireAuth = requireAuth;
	}


}
