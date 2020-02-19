package com.primeton.liuzhichao.demo.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import com.primeton.liuzhichao.demo.DemoApplication;
import com.primeton.liuzhichao.demo.dao.IMenuMapper;
import com.primeton.liuzhichao.demo.dao.IUserMapper;
import com.primeton.liuzhichao.demo.entity.Menu;
import com.primeton.liuzhichao.demo.entity.User;
import com.primeton.liuzhichao.demo.service.UserServiceImpl;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class SercurityControllerTestCase {
	
	@Autowired
	UserServiceImpl userService;
	
	@Autowired
    IUserMapper userMapper;
	
	@Autowired
	IMenuMapper menuMapper;
	
	//@Test
	public void getUserByName() {
		User user = userMapper.getUserByName2("张三");
		System.out.println("sercurity用户信息："+user.toString());
	}
	
	@Test
	public void getMenus() {
		List<Menu> menus = menuMapper.getMenus();
		for(Menu menu:menus) {
			System.out.println("------------menus----------------："+menu.toString());
		}
		
	}
}
