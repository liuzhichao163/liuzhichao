package com.primeton.liuzhichao.ums.application.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.primeton.liuzhichao.ums.application.annotate.LogAop;
import com.primeton.liuzhichao.ums.application.entity.PageInfoUser;
import com.primeton.liuzhichao.ums.application.entity.ResponseResult;
import com.primeton.liuzhichao.ums.application.entity.ResultEnum;
import com.primeton.liuzhichao.ums.application.entity.User;
import com.primeton.liuzhichao.ums.application.entity.UserAndOrg;
import com.primeton.liuzhichao.ums.application.service.IUserService;
import com.primeton.liuzhichao.ums.application.util.ValidatorUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;

/**
 * 用户管理控制器层代码
 * 
 * @author ASUS
 *
 */
@Api(value = "用户controller", tags = { "用户操作接口" })
@RequestMapping("/api/user")
@RestController
public class UserController extends BaseController {

	@Autowired
	private IUserService userService;

	/**
	 * 注册新用户 user 用户数据实体类
	 */
	@ApiOperation(value = "添加员工", tags = { "" }, notes = "")
	@PostMapping()
	public ResponseResult<Void> createUser(@RequestBody User user) {
		// 验证数据的有效性
		boolean result = ValidatorUtil.checkUsername(user.getUserName());
		// 验证用户名格式
		if (!result) {
			return new ResponseResult<Void>(ResultEnum.ERROR_NAME_UNQUALIFIED);
		}
		// 验证密码格式
		result = ValidatorUtil.checkPassword(user.getUserPassword());
		if (!result) {
			return new ResponseResult<Void>(ResultEnum.ERROR_PWD_UNQUALIFIED);
		}

		// 调用业务层的reg()方法实现注册功能
		userService.createUser(user);
			// 封装返回结果
			return new ResponseResult<Void>(ResultEnum.SUCCESS);
	}

	/**
	 * 登录功能
	 * 
	 * @param userName     姓名
	 * @param userPassword 密码
	 * @param session      用于存放id
	 * @return json
	 */
	@ApiOperation(value = "用户登录", tags = { "" }, notes = "")
	@PostMapping("/login")
	public ResponseResult<Void> login(@RequestBody User user, HttpSession session) {
		// 验证用户名格式
		boolean result = ValidatorUtil.checkUsername(user.getUserName());
		if (!result) {
			return new ResponseResult<Void>(ResultEnum.ERROR_NAME_UNQUALIFIED);
		}
		// 验证密码格式
		result = ValidatorUtil.checkPassword(user.getUserPassword());
		if (!result) {
			return new ResponseResult<Void>(ResultEnum.ERROR_PWD_UNQUALIFIED);
		}
		User data = userService.login(user.getUserName(), user.getUserPassword());
		// 登录成功后，将用户sessionId和用户名绑定到session上，用于验证（没有登录过，就不能显示主页）
		session.setAttribute("id", data.getId());
		session.setAttribute("userName", data.getUserName());
		return new ResponseResult<Void>(ResultEnum.SUCCESS);
	}

	/**
	 * 修改密码
	 * 
	 * @param oldPassword 原密码
	 * @param newPassword 新密码
	 * @param session     用于存放id
	 * @return json
	 */
	@ApiOperation(value = "修改密码", tags = { "" }, notes = "")
	@LogAop(name = "/api/user.action")
	@PutMapping()
	public ResponseResult<Void> modifyPassword(@RequestBody User user, HttpSession session) {
		// 获得登录后绑定到session上的用户id
		Integer id = getIdFromSession(session);
		// 调用业务层方法将id，新密码和旧密码传过去
		userService.modifyPassword(id, user.getUserPassword(), user.getNewPassword());
		return new ResponseResult<Void>(ResultEnum.SUCCESS);
	}

	/**
	 * 查询用户列表
	 * 
	 * @param number number[0]页数；number[1]每页条数
	 * @return 分页数据对象
	 */
	@ApiOperation(value = "查询所有员工", tags = { "" }, notes = "参数为Integer类型数组")
	@LogAop(name = "/api/user.action")
	@GetMapping("/show/{pageIndex}/{pageSize}")
	public PageInfoUser queryUsers(@PathVariable(name = "pageIndex", required = true) Integer pageIndex,
			@PathVariable(name = "pageSize", required = true) Integer pageSize) {
		return userService.queryUsers(pageIndex, pageSize);
	}

	/**
	 * 根据员工编号查询用户信息
	 */
	@ApiOperation(value = "查询单个员工", tags = { "" }, notes = "")
	@LogAop(name = "/api/user.action")
	@GetMapping("/{userId}")
	public UserAndOrg getUserByUserId(
			@PathVariable @ApiParam(name = "empId", value = "员工编号", required = true) String userId) {
		return userService.getUserByUserId(userId);
	}

	/**
	 * 删除指定id的用户
	 * 
	 * @param userIds 用户Id数组
	 * @return json
	 */
	@ApiOperation(value = "删除员工", tags = { "" }, notes = "可以删除一个用户，也可以删除多个用户，参数是Integer类型数组")
	@LogAop(name = "/api/user.action")
	@DeleteMapping("/{ids}")
	public ResponseResult<Void> removeUserByIds(
			@PathVariable @ApiParam(name = "ids", value = "员工id", required = true) Integer[] ids, HttpSession session) {
		ResponseResult<Void> rr = null;
		// 查询当前用户的id
		Integer id = getIdFromSession(session);
		// 调用业务层删除方法,将当前用户id传入判断用户权限
		userService.removeUserByIds(ids, id);
		return new ResponseResult<Void>(ResultEnum.SUCCESS);
	}

}
