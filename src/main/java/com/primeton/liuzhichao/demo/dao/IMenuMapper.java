package com.primeton.liuzhichao.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.primeton.liuzhichao.demo.entity.Menu;

@Mapper
public interface IMenuMapper {
	
	/**
	 * 查询所有菜单列表  带权限
	 * @return
	 */
	List<Menu> getMenus();
	
	/**
	 * 查询所有菜单列表 不带权限
	 * 
	 */
	List<Menu> getMenus2();
	
	/**
	 * 根据uid查询菜单
	 * @return
	 */
	List<Menu> getMenusByUid(Integer integer);
	
	/**
	 * 新增菜单
	 * @param menu
	 * @return
	 */
	Integer addMneu(Menu menu);
	

}
