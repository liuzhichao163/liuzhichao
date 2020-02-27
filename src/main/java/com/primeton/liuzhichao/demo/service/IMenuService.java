package com.primeton.liuzhichao.demo.service;

import java.util.List;

import com.primeton.liuzhichao.demo.entity.Menu;

/**
 * 系统菜单处理类
 * @author ASUS
 *
 */
public interface IMenuService {
	
	/**
	 * 查询全部的菜单列表，包含权限
	 * @return
	 */
	List<Menu> getMenus();
	
	/**
	 * 根据当前用户id查询菜单
	 */
	List<Menu> getMenuByUserId();
	
	/**
	 * 新增菜单
	 */
	Integer addMenu(Menu menu);
	
	/**
	 * 修改菜单
	 */
	//Integer updateMneu(Menu menu);

}
