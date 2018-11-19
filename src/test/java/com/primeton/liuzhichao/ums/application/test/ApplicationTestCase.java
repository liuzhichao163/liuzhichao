package com.primeton.liuzhichao.ums.application.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.primeton.liuzhichao.ums.application.UserApplication;
import com.primeton.liuzhichao.ums.application.controller.OrgController;
import com.primeton.liuzhichao.ums.application.controller.UserController;
import com.primeton.liuzhichao.ums.application.dao.IOrgMapper;
import com.primeton.liuzhichao.ums.application.entity.Org;
import com.primeton.liuzhichao.ums.application.entity.PageInfoUser;
import com.primeton.liuzhichao.ums.application.entity.ResponseResult;
import com.primeton.liuzhichao.ums.application.entity.User;
import com.primeton.liuzhichao.ums.application.service.IOrgService;
import com.primeton.liuzhichao.ums.application.service.IUserService;

/**
 * 单元测试类
 * @author ASUS
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
public class ApplicationTestCase {
	@Autowired
	private WebApplicationContext context;
	private MockMvc mockMvc;
	private MockHttpSession session;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		session = new MockHttpSession();
	}

	@Autowired
	private UserController userController;
	@Autowired
	private IUserService userService;
	@Autowired
	private OrgController orgController;
	@Autowired 
	private IOrgService orgService;


	//=================员工管理测试================
	/**
	 * 测试用户注册
	 */
	@Test
	public void testCreateUser() {
		User data = new User();
		data.setUserName("单元测试3");
		data.setUserPassword("111111");
		ResponseResult<Void> rr = userController.createUser(data);
		User user = userService.getUserByUserName("单元测试3");
		assertEquals("200", rr.getState()+"");
		assertNotNull(user);

	}

	/**
	 *  根据id删除用户功能
	 */
	@Test
	public void testRemoveUser() {
		Integer[] userIds = {89};
		session.setAttribute("id", 56);
		ResponseResult<Void> rr = userController.removeUserByIds(userIds,session);
		User user = userService.getUserById(89);
		Assert.assertEquals("200", rr.getState()+"");
		assertNull(user);
	}



	/**
	 * 用户登录功能
	 * 
	 * @throws Exception
	 */
	@Test
	public void testHandleLogin(){

		User user = new User();
		user.setUserName("马云");
		user.setUserPassword("222222");
		ResponseResult<Void> rr= userController.login(user, session);
		Assert.assertEquals("200", rr.getState()+"");
	}

	/**
	 * 修改用户密码
	 */
	@Test
	public void testModifyUser() {
		session.setAttribute("id", 60);
		User user = new User();
		user.setUserPassword("333333");
		user.setNewPassword("222222");
		ResponseResult<Void> rr = userController.modifyPassword(user, session);
		Assert.assertEquals("200", rr.getState()+"");
	}

	//=============部门管理测试==============


	/**
	 * 添加部门
	 */
	@Test
	public void testCreateOrg() {
		session.setAttribute("id", 56);
		Org org = new Org();
		org.setpId("111");
		org.setOrgName("单元测试2");
		ResponseResult<Void> rr = orgController.createOrg(org);
		Assert.assertEquals("200", rr.getState()+"");
		assertNull(orgService.getOrgByName("单元测试2"));

	}

	/**
	 * 查询子部门测试
	 */
	@Test
	public void testQueryOrgByDno() {
		List<Org> list = orgController.queryOrgByOrgId("A000");

		Assert.assertEquals(list.get(0).getOrgName(),"淘宝");
	}

	/**
	 * 查询部门员工信息
	 */
	@Test
	public void testQueryUserByOrgId() {

		PageInfoUser pageInfoUser = orgController.queryUsersByOrgId("A000",1,2);
		assertNotNull(pageInfoUser);
	}





}
