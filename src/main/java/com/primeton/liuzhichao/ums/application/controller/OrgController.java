package com.primeton.liuzhichao.ums.application.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.primeton.liuzhichao.ums.application.annotate.LogAop;
import com.primeton.liuzhichao.ums.application.entity.Org;
import com.primeton.liuzhichao.ums.application.entity.PageInfoUser;
import com.primeton.liuzhichao.ums.application.entity.ResponseResult;
import com.primeton.liuzhichao.ums.application.entity.ResultEnum;
import com.primeton.liuzhichao.ums.application.service.IOrgService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 部门管理Controller操作层
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
	 */
	@ApiOperation(value = "添加部门", tags = { "" }, notes = "")
	@LogAop(name = "/api/org.action")
	@PostMapping()
	public ResponseResult<Void> createOrg(Org org) {
		orgService.createOrg(org);
		return new ResponseResult<Void>(ResultEnum.SUCCESS);
	}

	/**
	 * 根据部门编号查询子级部门信息
	 * 
	 * @param orgId 部门编号
	 * @return 子集部门信息集合
	 */
	@ApiOperation(value = "查询子部门", tags = { "" }, notes = "")
	@GetMapping("/{orgId}")
	public List<Org> queryOrgByOrgId(
			@PathVariable @ApiParam(name = "orgId", value = "部门编号", required = true) String orgId) {

		return orgService.queryOrgByOrgId(orgId);
	}

	/**
	 * 根据部门编号分页查询部门员工信息
	 * 
	 * @param orgId 部门编号
	 * @param  pageIndex  查询页数
	 * @param  pageSize   每页条数
	 * @return 分页数据对象
	 */
	@ApiOperation(value = "查询部门员工", tags = { "" }, notes = "")
	@GetMapping("show/{orgId}/{pageIndex}/{pageSize}")
	public PageInfoUser queryUsersByOrgId(
			@PathVariable @ApiParam(name = "orgId", value = "部门编号", required = true) String orgId,
			@PathVariable @ApiParam(name = "pageIndex", value = "查询页数", required = true) Integer pageIndex,
			@PathVariable @ApiParam(name = "pageSize", value = "每页条数", required = true) Integer pageSize) {
		return orgService.queryUserByOrgId(orgId, pageIndex, pageSize);
	}

	/**
	 * 根据部门编号删除部门，权限功能
	 * 
	 * @param orgId   部门编号
	 * @param session 从session中获取用户的id，获取职位
	 * @return json
	 */

	@ApiOperation(value = "删除部门", tags = { "" }, notes = "")
	@LogAop(name = "/api/org.action")
	@DeleteMapping("/{orgId}")
	public ResponseResult<Void> removeOrgByOrgId(
			@PathVariable @ApiParam(name = "orgId", value = "部门编号", required = true) String orgId,
			HttpSession session) {
		// 查询当前用户的id
		Integer id = getIdFromSession(session);
		// 调用删除方法
		orgService.removeOrgByOrgId(orgId, id);
		return new ResponseResult<Void>(ResultEnum.SUCCESS);
	}

}
