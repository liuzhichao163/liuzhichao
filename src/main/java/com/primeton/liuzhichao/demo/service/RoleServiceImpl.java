package com.primeton.liuzhichao.demo.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.fabric.xmlrpc.base.Array;
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

	@Override
	public Integer addRoleMenu(String[] mids, String rid) {
		Integer addResult = null;
		Integer delResult = null;
		List<String> list = roleMapper.queryMidsById(rid);
		List<String> delList = new ArrayList<>(list.size());
		List<String> addList = new ArrayList<>(mids.length);
		delList.addAll(list);
		Collections.addAll(addList, mids);  //数组转list
		//取delList和newList的差集得到要删除的mid
		delList.removeAll(addList);
		//取newList和addList的差集得到要新增的mid
		addList.removeAll(list);
		
		if(addList.size() > 0) {
			String[] add = new String[addList.size()];
			addList.toArray(add);
			addResult = roleMapper.addRoleMenu(add, rid);
		}
		if(delList.size() > 0) {
			String[] del = new String[delList.size()];
			delList.toArray(del);
			delResult = roleMapper.deteleMenuRoleByid(del, rid);
		}
		return addResult;
	}

}
