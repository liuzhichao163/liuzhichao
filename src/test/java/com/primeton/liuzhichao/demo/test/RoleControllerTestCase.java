package com.primeton.liuzhichao.demo.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.pagehelper.PageInfo;
import com.primeton.liuzhichao.demo.DemoApplication;
import com.primeton.liuzhichao.demo.dao.IRoleMapper;
import com.primeton.liuzhichao.demo.entity.Role;
import com.primeton.liuzhichao.demo.service.IRoleService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class RoleControllerTestCase {
	
	@Autowired
	IRoleMapper roleMapper;
	
	@Autowired
	IRoleService roleService;
	
	//@Test
	public void getRoles() {
		String userName = "";
		List<Role> roles = roleMapper.getRoles(userName);
		for(Role role : roles) {
			List<String> mids = role.getMids();
			if(mids.size()>0) {
				for(int i=0;i<mids.size();i++) {
					System.out.println("mid:"+mids.get(i));
				}
			}
		}
	}
	
	@Test
	public void getRoles2() {
		String userName = "";
		PageInfo<Role> page = roleService.getRoles(userName, 1, 5);
		List<Role> roles = page.getList();
		for(Role role : roles) {
			List<String> mids = role.getMids();
			if(mids.size()>0) {
				for(int i=0;i<mids.size();i++) {
					System.out.println("mid:"+mids.get(i));
				}
			}
		}
	}
}
