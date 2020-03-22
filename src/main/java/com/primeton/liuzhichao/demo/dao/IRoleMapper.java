package com.primeton.liuzhichao.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.primeton.liuzhichao.demo.entity.Menu;
import com.primeton.liuzhichao.demo.entity.Role;

@Mapper
public interface IRoleMapper {
	
	/**
	 * 新增角色
	 * @param role  角色对象
	 */
	Integer addRole(Role role);
	
	/**
	 * 根据角色名称查询角色信息
	 * @param nameZH 角色名称
	 * @return 角色信息
	 */
	Role getRoleByNameZH(String nameZH);
	
	/**
	 * 查询角色列表/根据角色名称查询角色信息
	 *注意： 因为sql要用到<if>标签查询，查询参数为userName，所以这要使用@Param注解
	 * @param userName  角色名称
	 * @return 
	 */
	List<Role> getRoles(@Param("userName") String userName);
	
	/**
	 * 根据id修改角色
	 * @param id
	 * @return
	 */
	Integer updateRoleById(Role role);
	
	/**
	 * 根据角色id删除角色
	 * @param role
	 * @return
	 */
	Integer deteleRoleById(Integer id);
	
	/**
	 *给角色授权菜单 
	 * @param mids   菜单id数组
	 * @param rid    角色id
	 * @return
	 */
	Integer addRoleMenu(String[] mids,String rid);
	
	/**
	 * 根据rid查询mid
	 */
	List<String> queryMidsById(String id);
	
	/**
	 * 根据rid和mid删除记录
	 */
	Integer deteleMenuRoleByid(@Param("mids") String[] mids,@Param("rid") String rid);
}
