package com.primeton.liuzhichao.demo.service;

import java.util.List;

import com.primeton.liuzhichao.demo.entity.Org;
import com.primeton.liuzhichao.demo.entity.PageInfoUser;
import com.primeton.liuzhichao.demo.exception.DemoException;

/**
 * 部门管理业务层接口
 * 
 * @author ASUS
 *
 */
public interface IOrgService {
	/**
	 * 添加部门
	 * 
	 * @param org 部门数据对象
	 * @return 部门数据对象
	 * @throws DemoException 此部门是否已存在、 添加失败
	 */
	Org createOrg(Org org) throws DemoException;

	/**
	 * 根据部门名称查询部门信息
	 * 
	 * @param orgName 部门名称
	 * @return 部门数据对象
	 */
	Org getOrg(String orgName);

	/**
	 * 根据部门编号删除部门
	 * 
	 * @param orgId 部门编号
	 * @return 生效行数，1代表成功，0代表失败
	 * @throws DemoException 删除部门失败
	 */
	Integer removeOrg(String orgId) throws DemoException;

	/**
	 * 根据部门编号查询下级子级部门
	 * 
	 * @return 下级子集部门集合
	 * @return DemoException 部门无下级部门
	 */
	List<Org> queryOrgs(String orgId) throws DemoException;

	/**
	 * 根据部门编号查询此部门员工信息
	 * 
	 * @param orgId 部门编号
	 * @return 员工信息分页对象
	 * @throws DemoException 此部门没有员工
	 */
	PageInfoUser queryUsers(String orgId, Integer pageNum, Integer pageSize) throws DemoException;

}
