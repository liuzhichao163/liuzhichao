package com.primeton.liuzhichao.demo.service;


import com.github.pagehelper.PageInfo;
import com.primeton.liuzhichao.demo.entity.Role;

public interface IRoleService {
	/**
	 * 新增角色
	 * @param role
	 * @return
	 */
	Integer addRole(Role role);
	
	/**
	 * 查询角色列表/根据角色名称查询角色信息
	 * @param userName  角色名称
	 * @param page      起始页数
	 * @param size		每页条数
	 * @return
	 */
	PageInfo<Role> getRoles(String userName,Integer page,Integer size);
	
	/**
	 * 根据Id修改角色信息
	 */
	Integer updateRoleById(Role role);
	
	/**
	 * 根据角色id删除角色信息
	 */
	Integer deleteRole(Integer id);
}
