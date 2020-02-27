package com.primeton.liuzhichao.demo.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.ToString;

@ToString
public class Menu implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1542386831250271889L;
	
	private Long id;
	private String url;
	private String path;
	private String component;
	private String name;
	private String iconCls;
	private Long parentId;
	private Long enabled;
	private MenuMeta meta;
	private List<Role> roles;
	private List<Menu> children;
	
	public Menu() {
		super();
	}

	public Menu(Long id, String url, String path, String component, String name, String iconCls, Long parentId,
			Long enabled, MenuMeta meta, List<Role> roles, List<Menu> children) {
		super();
		this.id = id;
		this.url = url;
		this.path = path;
		this.component = component;
		this.name = name;
		this.iconCls = iconCls;
		this.parentId = parentId;
		this.enabled = enabled;
		this.meta = meta;
		this.roles = roles;
		this.children = children;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@JsonIgnore
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	@JsonFormat(shape = JsonFormat.Shape.OBJECT)
	public Object getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	
	@JsonIgnore
	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public MenuMeta getMeta() {
		return meta;
	}

	public void setMeta(MenuMeta meta) {
		this.meta = meta;
	}
	
	//json序列化时将添加此注解的属性忽略掉
	@JsonIgnore
	public List<Role> getRoles() {
		return roles;
	}
	
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}

	public Long getEnabled() {
		return enabled;
	}

	public void setEnabled(Long enabled) {
		this.enabled = enabled;
	}


	
	
	

	
	
	
	
	

}
