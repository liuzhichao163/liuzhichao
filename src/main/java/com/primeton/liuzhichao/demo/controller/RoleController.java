package com.primeton.liuzhichao.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.primeton.liuzhichao.demo.entity.ResponseResult;
import com.primeton.liuzhichao.demo.entity.Role;
import com.primeton.liuzhichao.demo.exception.ExceptionEnum;
import com.primeton.liuzhichao.demo.service.IRoleService;

@RestController
@RequestMapping("/api/role")
public class RoleController {
	
	@Autowired
	IRoleService roleService;
	
	@PostMapping()
	public ResponseResult<Void> addRole(Role role){
	    roleService.addRole(role);
		return new ResponseResult<Void>(ExceptionEnum.SUCCESS);
	}
	
	@GetMapping()
	public PageInfo<Role> getRoles(String nameZH,Integer page,Integer size){
		PageInfo<Role> pageInfo = roleService.getRoles(nameZH, page, size);
		return pageInfo;
	}
	
	@PutMapping()
	public ResponseResult<Void> updateRole(@RequestBody Role role){
		roleService.updateRoleById(role);
		return new ResponseResult<Void>(ExceptionEnum.SUCCESS);
	}
	
	@DeleteMapping()
	public ResponseResult<Void> deleteRole(Integer id){
		roleService.deleteRole(id);
		return new ResponseResult<Void>(ExceptionEnum.SUCCESS);
	}
	
	@PostMapping("/menu")
	public ResponseResult<Void> addRoleMenu(String[] mids, String rid){
		roleService.addRoleMenu(mids, rid);
		return new ResponseResult<Void>(ExceptionEnum.SUCCESS);
	}
	
}
