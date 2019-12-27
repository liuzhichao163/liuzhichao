package com.primeton.liuzhichao.demo.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.primeton.liuzhichao.demo.DemoApplication;
import com.primeton.liuzhichao.demo.controller.OrgController;
import com.primeton.liuzhichao.demo.dao.IOrgMapper;
import com.primeton.liuzhichao.demo.entity.Org;
import com.primeton.liuzhichao.demo.entity.ResponseResult;
import com.primeton.liuzhichao.demo.exception.DemoException;

/**
 * 单元测试类
 * 
 * @author ASUS
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class OrgControllerTestCase {

	@Autowired
	private OrgController orgController;
	@Autowired
	private IOrgMapper orgMapper;

	// =============部门管理测试==============

	@Test
	public void testOrg() throws DemoException {
		testRemoveOrg();
		testCreateOrg();
		testQueryOrg();
	}

	/**
	 * 添加部门
	 * @throws DemoException 
	 */
	
	public void testCreateOrg() throws DemoException {
		Org org = new Org();
		org.setOrgId("111");
		org.setpId("222");
		org.setOrgName("单元测试部门");
		ResponseResult<Void> rr = orgController.createOrg(org);
		Assert.assertEquals("200", rr.getState() + "");
		assertNotNull(orgMapper.getOrgByName("单元测试部门"));
	}

	/**
	 * 删除部门
	 * @throws DemoException 
	 */
	public void testRemoveOrg() throws DemoException {
		orgController.removeOrg("111");
		assertNull(orgMapper.getOrgByOrgId("111"));

	}

	/**
	 * 查询子部门测试
	 * @throws DemoException 
	 */
	
	public void testQueryOrg() throws DemoException {
		List<Org> list = orgController.queryChilds("A000");
		Assert.assertEquals(list.get(0).getOrgName(), "淘宝");
	}

	
}
