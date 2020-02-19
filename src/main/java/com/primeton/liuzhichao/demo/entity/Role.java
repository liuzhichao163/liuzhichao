package com.primeton.liuzhichao.demo.entity;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Role implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7061887301676748212L;
	
	private Integer id;
	private String name;
	private String nameZH;
	
	public Role() {
		super();
	}
	
	public Role(Integer id, String name, String nameZH) {
		super();
		this.id = id;
		this.name = name;
		this.nameZH = nameZH;
	}

	
	
}
