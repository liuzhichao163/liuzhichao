package com.primeton.liuzhichao.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.primeton.liuzhichao.demo.entity.Menu;
import com.primeton.liuzhichao.demo.service.IMenuService;
/**
 * 只要登录就能访问，因为"/api/menu"不在数据库liuzhichao_menu表中，
 * 所有在CustomMetadataSource拦截器中获取到的权限集合为"ROLE_LOGIN",即登录技能访问的权限
 * 所以在UrlAccessDecisionManager拦截器中可以将此url放行
 * @author ASUS
 *
 */
@RestController
@RequestMapping("/api/menu")
public class MenuController {
	
	@Autowired
	IMenuService menuService;
	
	/**
	 * 根据用户id查询菜单列表
	 * @return
	 */
	@GetMapping()
	public List<Menu> getMenuByUserId(){
		return menuService.getMenuByUserId();
	}

}
