package com.primeton.liuzhichao.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.primeton.liuzhichao.demo.dao.IMenuMapper;
import com.primeton.liuzhichao.demo.entity.Menu;
import com.primeton.liuzhichao.demo.utils.Utils;

@Service
public class MenuServiceImpl implements IMenuService{
	
	@Autowired
	IMenuMapper menuMapper;
	
	@Override
	public List<Menu> getMenus() {
		
		return menuMapper.getMenus();
	}

	@Override
//	@RedisCache
	public List<Menu> getMenuByUserId() {
		return menuMapper.getMenusByUid(Utils.getCurrentUser().getId());
	}

	@Override
	public Integer addMenu(Menu menu) {
		Integer result = menuMapper.addMneu(menu);
		return result;
	}

//	@Override
//	public Integer updateMneu(Menu menu) {
//		Integer result = menuMapper.updateMneu(menu);
//		return result;
//	}


}
