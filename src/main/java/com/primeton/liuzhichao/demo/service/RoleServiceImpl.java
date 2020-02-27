package com.primeton.liuzhichao.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.primeton.liuzhichao.demo.dao.IRoleMapper;
import com.primeton.liuzhichao.demo.entity.Role;
import com.primeton.liuzhichao.demo.exception.DemoException;
import com.primeton.liuzhichao.demo.exception.ExceptionEnum;
import com.primeton.liuzhichao.demo.redis.RedisLock;

@Service
public class RoleServiceImpl implements IRoleService{
	
	@Autowired
	IRoleMapper roleMapper;
	
	@Autowired
	RedisLock redisLock;
	
	@Override
	public Integer addRole(Role role){
		Integer result = null;
		Role newRole = null;
		String nameZH = role.getNameZH();
		try {
			Boolean lock = redisLock.setLock(nameZH);
			if(lock) {
				newRole = getRoleByNameZh(nameZH);
				if(newRole == null) {
					result = roleMapper.addRole(role);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			redisLock.unLock(nameZH);
		}
		if(newRole != null) {
			throw new DemoException(ExceptionEnum.ERROR_ROLE_NAMEZH);
		}
		return result;
	}
	
	/**
	 * 根据角色名称查询角色信息
	 * @param nameZH
	 * @return
	 */
	public Role getRoleByNameZh(String nameZH) {
		Role role = roleMapper.getRoleByNameZH(nameZH);
		return role;
	}


	@Override
	public PageInfo<Role> getRoles(String userName, Integer page, Integer size) {
		PageHelper.startPage(page, size);
		List<Role> roles = roleMapper.getRoles(userName);
		PageInfo<Role> pageInfo = new PageInfo<>(roles);
		return pageInfo;
	}

	@Override
	public Integer updateRoleById(Role role) {
		Integer result = null;
		if(getRoleByNameZh(role.getNameZH()) == null) {
			roleMapper.updateRoleById(role);
		}else {
			throw new DemoException(ExceptionEnum.ERROR_ROLE_NAMEZH);
		}
		return result;
	}

	@Override
	public Integer deleteRole(Integer id) {
		Integer result = null;
		if(id != null) {
			roleMapper.deteleRoleById(id);
		}else {
			throw new DemoException(ExceptionEnum.ERROR_ROLE_ID);
		}
		return result;
	}

}
