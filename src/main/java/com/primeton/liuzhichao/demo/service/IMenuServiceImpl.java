package com.primeton.liuzhichao.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.primeton.liuzhichao.demo.dao.IMenuMapper;
import com.primeton.liuzhichao.demo.entity.Menu;
import com.primeton.liuzhichao.demo.utils.Utils;

@Service
public class IMenuServiceImpl implements IMenuService{
	
	@Autowired
	IMenuMapper menuMapper;
	
	@Override
	public List<Menu> getMenus() {
		
		return menuMapper.getMenus();
	}

	@Override
	public List<Menu> getMenuByUserId() {
		System.out.println("---当前用户id------:"+Utils.getCurrentUser());
		return menuMapper.getMenusByUid(Utils.getCurrentUser().getId());
	}


}
