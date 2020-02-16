package com.primeton.liuzhichao.demo.controller;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.primeton.liuzhichao.demo.entity.PageInfoUser;
import com.primeton.liuzhichao.demo.entity.ResponseResult;
import com.primeton.liuzhichao.demo.entity.User;
import com.primeton.liuzhichao.demo.entity.UserAndOrg;
import com.primeton.liuzhichao.demo.exception.ExceptionEnum;
import com.primeton.liuzhichao.demo.service.IOrgService;
import com.primeton.liuzhichao.demo.service.IUserService;
import com.primeton.liuzhichao.demo.utils.PoiUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 用户Controller层Api
 * 
 * @author ASUS
 *
 */
@Api(value = "用户controller", tags = { "用户操作接口" })
@RequestMapping("/api/users")
@RestController
public class UserController extends BaseController {

	@Autowired
	private IUserService userService;
	@Autowired
	private IOrgService orgService;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	
	/**
	 * 注册新用户 user 用户数据实体类
	 * 
	 * @throws DemoException 添加失败抛出异常
	 */
	@ApiOperation(value = "添加员工")
	@PostMapping()
	public ResponseResult<Void> createUser(@RequestBody User user){
		System.out.println("======"+JSON.toJSONString(user));
		userService.createUser(user);
		return new ResponseResult<Void>(ExceptionEnum.SUCCESS);
	}

	/**
	 * 删除指定id的用户
	 * 
	 * @param userIds 用户Id
	 * @return json
	 * @throws DemoException 自定义异常 删除失败抛出异常
	 */
	@ApiOperation(value = "删除员工")
	@DeleteMapping("/{id}")
	public ResponseResult<Void> removeUser(
			@PathVariable @ApiParam(name = "id", value = "员工id", required = true) Integer id){
		userService.removeUser(id);
		return new ResponseResult<Void>(ExceptionEnum.SUCCESS);
	}

	/**
	 * 修改密码
	 * 
	 * @param user    用户数据对象
	 * @param session 用户获取id
	 * @return
	 * @throws DemoException 自定义异常 修改失败抛出异常
	 */
	@ApiOperation(value = "修改密码")
	@PutMapping("/actions/password")
	public ResponseResult<Void> modifyPassword(@RequestBody User user) {
		// 调用业务层方法将id，新密码和旧密码传过去
		userService.modifyPassword(user.getId(), user.getOldPassword(), user.getNewPassword());
		return new ResponseResult<Void>(ExceptionEnum.SUCCESS);
	}

	/**
	 * 根据id修改用户信息
	 * 
	 * @param user    用户数据对象
	 * @param session 用于存放id 修改信息失败抛出异常
	 * @return json
	 * @throws DemoException
	 */
	@ApiOperation(value = "修改用户信息")
	@PutMapping()
	public ResponseResult<Void> modifyUser(@RequestBody User user){
		userService.modifyUser(user);
		return new ResponseResult<Void>(ExceptionEnum.SUCCESS);
	}


	/**
	 * 查询用户数据(可模糊查询)
	 * @param userName
	 * @return
	 */
	@ApiOperation(value = "查询部门员工")
	@GetMapping()
	public PageInfoUser getUserByName(
			@RequestParam @ApiParam(name = "userName", value = "用户姓名", required = true) String userName,
			@RequestParam @ApiParam(name = "pageIndex", value = "页数", required = true) Integer pageIndex,
			@RequestParam @ApiParam(name = "pageSize", value = "每页条数", required = true) Integer pageSize
			){
		return userService.getUserByName(userName,pageIndex,pageSize);
		
	}
	
	/**
	 * 根据部门编号查询部门员工信息
	 * 
	 * @param orgId     部门编号
	 * @return 分页数据对象
	 * @throws DemoException 自定义异常 查询失败抛出异常
	 */
	@ApiOperation(value = "查询部门员工")
	@GetMapping("/orgId")
	public PageInfoUser queryUsers(
			@RequestParam @ApiParam(name = "orgId", value = "部门编号", required = true) String orgId)
			{
		return orgService.queryUsers(orgId);
	}
	
	

	/**
	 * 登录功能
	 * 
	 * @param userName     姓名
	 * @param userPassword 密码
	 * @param session      用于存放id
	 * @return json
	 * @throws DemoException 自定义异常 登录失败抛出异常
	 */
	@ApiOperation(value = "用户登录")
	@PostMapping("/actions/login")
	public ResponseResult<Void> login(@RequestBody User user, HttpSession session){
		UserAndOrg data = userService.login(user.getName(), user.getPassword());
		session.setAttribute("id", data.getId());
		session.setAttribute("userName", data.getUserName());
		return new ResponseResult<Void>(ExceptionEnum.SUCCESS);
	}
	
	/**
	 * 导出员工信息到excel
	 * @return 
	 */
	@GetMapping("/exportEmp")
	public ResponseEntity<byte[]> exportEmp(){
		return userService.exportEmp();
	}
	/**
	 * 导入员工信息的excel
	 * @param file  员工信息excel
	 * @return
	 */
	@PostMapping("/importEmp")
	public ResponseResult<Void> importEmp(MultipartFile file){
		List<User> userLlist = PoiUtils.importEmp(file);
		for(int i=0; i<userLlist.size(); i++) {
			userService.createUser(userLlist.get(i));
		}
		return new ResponseResult<Void>(ExceptionEnum.SUCCESS);
	}
	
	
	/**
	 * 查询用户列表
	 * 
	 * @param number pageIndex页数；pageSize 每页条数
	 * @return 分页数据对象
	 */
//	@ApiOperation(value = "查询所有员工", notes = "参数为Integer类型数组")
//	@GetMapping()
//	public PageInfoUser queryUsers(
//			@RequestParam @ApiParam(name = "pageIndex", value = "页数", required = true) Integer pageIndex,
//			@RequestParam @ApiParam(name = "pageSize", value = "每页条数", required = true) Integer pageSize) {
//		return userService.queryUsers(pageIndex, pageSize);
//	}

	@PostMapping("/upload/photo")
	public ResponseResult<Void> uploadPhoto(HttpServletRequest req, MultipartFile file){
		StringBuffer url = new StringBuffer();
		String filePath = "/images/"+sdf.format(new Date());
		//项目部署后的绝对路径
		String imgFolderPath = req.getServletContext().getRealPath(filePath);
		System.out.println("项目部署后的绝对路径:"+imgFolderPath);
		File imgFolder = new File(imgFolderPath);
		if(!imgFolder.exists()) {
			imgFolder.mkdirs();
		}
		String imgName = UUID.randomUUID() +"_"+ file.getOriginalFilename().replaceAll(" ", "");
		
		url.append(req.getScheme()) //当前页面所使用的协议https/http
			.append("//")
			.append(req.getServerName()) //当前服务器的名字
			.append(":")
			.append(req.getServerPort()) //当前服务器所使用的端口号
			.append(req.getContextPath()) //当前项目的根路径
			.append(filePath)
		    .append("/")
		    .append(imgName);
		
		System.out.println("url:"+url);
		try {
			IOUtils.write(file.getBytes(), new FileOutputStream(new File(imgFolder, imgName)));
			
			return new ResponseResult<Void>(ExceptionEnum.SUCCESS,url.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return new ResponseResult<Void>(ExceptionEnum.UNKONW_ERROR);
	}
}
