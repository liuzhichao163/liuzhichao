package com.primeton.liuzhichao.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.primeton.liuzhichao.demo.entity.Org;
import com.primeton.liuzhichao.demo.entity.PageInfoUser;
import com.primeton.liuzhichao.demo.entity.ResponseResult;
import com.primeton.liuzhichao.demo.exception.DemoException;
import com.primeton.liuzhichao.demo.exception.ExceptionEnum;
import com.primeton.liuzhichao.demo.service.IOrgService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 部门控制层Api
 * 
 * @author ASUS
 *
 */
@Api(value = "部门controller", tags = { "部门操作接口" })
@RequestMapping("/api/org")
@RestController
public class OrgController extends BaseController {

	// 声明部门业务层对象
	@Autowired
	private IOrgService orgService;

	/**
	 * 添加部门功能
	 * 
	 * @param orgId   部门编号
	 * @param orgName 部门名称
	 * @param orgLoc  部门地址
	 * @param pId     上级部门编号
	 * @return json
	 * @throws DemoException 自定义异常 添加失败抛出异常
	 */
	@ApiOperation(value = "添加部门")
	@PostMapping()
	public ResponseResult<Void> createOrg(Org org) throws DemoException {
		orgService.createOrg(org);
		return new ResponseResult<Void>(ExceptionEnum.SUCCESS);
	}

	/**
	 * 根据部门编号查询子级部门信息
	 * 
	 * @param orgId 部门编号
	 * @return 子集部门信息集合
	 * @throws DemoException 自定义异常  查询失败抛出异常
	 */
	@ApiOperation(value = "查询子部门")
	@GetMapping()
	public List<Org> queryOrgs(@RequestParam @ApiParam(name = "orgId", value = "部门编号", required = true) String orgId) throws DemoException {

		return orgService.queryOrgs(orgId);
	}

	/**
	 * 根据部门编号分页查询部门员工信息
	 * 
	 * @param orgId     部门编号
	 * @param pageIndex 查询页数
	 * @param pageSize  每页条数
	 * @return 分页数据对象
	 * @throws DemoException 自定义异常 查询失败抛出异常
	 */
	@ApiOperation(value = "查询部门员工")
	@GetMapping("/actions/users")
	public PageInfoUser queryUsers(
			@RequestParam @ApiParam(name = "orgId", value = "部门编号", required = true) String orgId,
			@RequestParam @ApiParam(name = "pageIndex", value = "查询页数", required = true) Integer pageIndex,
			@RequestParam @ApiParam(name = "pageSize", value = "每页条数", required = true) Integer pageSize) throws DemoException {
		return orgService.queryUsers(orgId, pageIndex, pageSize);
	}

	/**
	 * 根据部门编号删除部门，权限功能
	 * 
	 * @param orgId   部门编号
	 * @param session 从session中获取用户的id，获取职位
	 * @return json
	 * @throws DemoException 自定义异常 删除失败抛出异常
	 */

	@ApiOperation(value = "删除部门", tags = { "" }, notes = "")
	@DeleteMapping("/{orgId}")
	public ResponseResult<Void> removeOrg(
			@PathVariable @ApiParam(name = "orgId", value = "部门编号", required = true) String orgId) throws DemoException {
		// 调用删除方法
		orgService.removeOrg(orgId);
		return new ResponseResult<Void>(ExceptionEnum.SUCCESS);
	}

}
