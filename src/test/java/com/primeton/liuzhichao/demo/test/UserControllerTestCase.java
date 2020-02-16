package com.primeton.liuzhichao.demo.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.primeton.liuzhichao.demo.DemoApplication;
import com.primeton.liuzhichao.demo.controller.UserController;
import com.primeton.liuzhichao.demo.dao.IUserMapper;
import com.primeton.liuzhichao.demo.entity.PageInfoUser;
import com.primeton.liuzhichao.demo.entity.ResponseResult;
import com.primeton.liuzhichao.demo.entity.User;
import com.primeton.liuzhichao.demo.entity.UserAndOrg;
import com.primeton.liuzhichao.demo.exception.DemoException;
import com.primeton.liuzhichao.demo.service.IUserService;

/**
 * 单元测试类
 * 
 * @author ASUS
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class UserControllerTestCase {

	@Autowired
	private UserController userController;
	@Autowired
	private IUserService userService;
	@Autowired
	private IUserMapper userMapper;

	// =================员工管理测试================

	@Test
	public void testUser() throws DemoException {
		testRemoveUser();
		testCreateUser();
		testModifyUser();
		testModifyPassword();
		

	}

	/**
	 * 测试用户注册
	 * @throws DemoException 
	 */
	public void testCreateUser() throws DemoException {
		ResponseResult<Void> rr = null;
		User data = new User();
		data.setName("单元测试");
		data.setPassword("111111");
		data.setJob("销售");
		data.setMgrId("1111");
		data.setOrgId("1111");
		rr = userController.createUser(data);
		UserAndOrg userAndOrg = userMapper.getUserByName("单元测试");
		assertEquals("200", rr.getState() + "");
		assertNotNull(userAndOrg);
	}

	/**
	 * 修改用户信息
	 * @throws DemoException 修改信息失败抛出异常
	 */
	public void testModifyUser() throws DemoException {
		Integer id = userMapper.getUserByName("单元测试").getId();
		User user = new User();
		user.setId(id);
		user.setJob("客服");
		user.setMgrId("2222");
		user.setOrgId("2222");
		ResponseResult<Void> rr = userController.modifyUser(user);
		Assert.assertEquals("200", rr.getState() + "");

	}

	/**
	 * 修改用户密码
	 * @throws DemoException 
	 */
	public void testModifyPassword() throws DemoException {
		Integer id = userMapper.getUserByName("单元测试").getId();
		User user = new User();
		user.setId(id);
		user.setOldPassword("111111");
		user.setNewPassword("222222");
		ResponseResult<Void> rr = userController.modifyPassword(user);
		Assert.assertEquals("200", rr.getState() + "");
	}

	/**
	 * 根据id删除用户功能
	 * @throws DemoException 
	 */
	public void testRemoveUser() throws DemoException {
		Integer id = userMapper.getUserByName("单元测试").getId();
		ResponseResult<Void> rr = userController.removeUser(id);
		User user = userMapper.getUser(id);
		Assert.assertEquals("200", rr.getState() + "");
		assertNull(user);
	}
	
	


}
