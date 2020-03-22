package com.primeton.liuzhichao.demo.entity;

import java.io.Serializable;
import java.util.List;

import lombok.ToString;

//@Data
@ToString
public class Role implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7061887301676748212L;
	
	private Integer id;
	private String name;
	private String nameZH;
	private List<String> mids;  //所对应的菜单id
	
	public Role() {
		super();
	}

	public Role(Integer id, String name, String nameZH, List<String> mids) {
		super();
		this.id = id;
		this.name = name;
		this.nameZH = nameZH;
		this.mids = mids;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameZH() {
		return nameZH;
	}

	public void setNameZH(String nameZH) {
		this.nameZH = nameZH;
	}

	public List<String> getMids() {
		return mids;
	}

	public void setMids(List<String> mids) {
		this.mids = mids;
	}

	
	

	
	
}
