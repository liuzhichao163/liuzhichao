package com.primeton.liuzhichao.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.primeton.liuzhichao.demo.entity.Menu;

@Mapper
public interface IMenuMapper {
	
	/**
	 * 查询所有菜单列表
	 * @return
	 */
	List<Menu> getMenus();
	
	/**
	 * 根据用户id查询菜单
	 * @param UserId
	 * @return 菜单列表
	 */
	List<Menu> getMenusByUid(Integer userId);

}
