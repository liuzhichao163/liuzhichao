package com.primeton.liuzhichao.demo.service;


import org.springframework.http.ResponseEntity;

import com.primeton.liuzhichao.demo.entity.PageInfoUser;
import com.primeton.liuzhichao.demo.entity.User;
import com.primeton.liuzhichao.demo.entity.UserAndOrg;
import com.primeton.liuzhichao.demo.exception.DemoException;

/**
 * 用户Service层接口Api
 * 
 * @author ASUS
 *
 */

public interface IUserService {
	/**
	 * 注册功能
	 * 
	 * @param user 用户数据对象
	 * @return 返回注册成功的用户对象，包含用户id
	 * @throws DemoException 注册失败抛出DemoException异常
	 */
	UserAndOrg createUser(User user);

	/**
	 * 删除指定的用户信息，此功能是权限功能
	 * 
	 * @param ids 被删除的用户id数组
	 * @param id  当前此用户id，用来判断权限
	 * @return 生效行数
	 * @throws DemoException 抛出此用户不可删除异常、没有选中被删除的用户、权限不足
	 */
	Integer removeUser(Integer id);

	/**
	 * 根据id修改用户密码
	 * 
	 * @param id          用户登录后保存在session中的id
	 * @param oldPassword 数据库中存在的旧密码
	 * @param newPassword 新密码
	 * @throws DemoException 密码修改失败
	 * @return 返回生效行数
	 */
	Integer modifyPassword(Integer id, String oldPassword, String newPassword);

	/**
	 * 根据id修改用户信息
	 * 
	 * @param id    用户id
	 * @param job   职位
	 * @param MgrId 上级领导编号
	 * @param orgId 部门编号
	 * @return 生效行数
	 * @throws DemoException 信息修改失败
	 */
	Integer modifyUser(User user);

	/**
	 * 根据用户名查询用户数据对象
	 * 
	 * @param userName 用户名
	 * @return 用户数据对象，没有此用户则返回null
	 */
	PageInfoUser getUserByName(String userName,Integer pageIndex,Integer pageSize);

	/**
	 * 查询所有用户列表
	 * 
	 * @return 返回含有用户信息的集合
	 */
	PageInfoUser queryUsers(Integer pageNum, Integer pageSize);

	/**
	 * 登录功能
	 * 
	 * @param userName    用户名
	 * @param userPassword 用户密码
	 * @throws DemoException 密码不正确抛出异常 用户名不正确抛出异常
	 * @return 用户数据对象
	 */
	UserAndOrg login(String userName, String userPassword);
	
	/**
	 * 导出员工信息到excel
	 * @return
	 */
	ResponseEntity<byte[]> exportEmp();
	
	/**
	 * 上传、修改头像
	 * @param newUserFace  新头像的保存路径
	 * @param httpUrl      头像的访问路径
	 * @param oldUserFace  旧头像的访问路径，第一次上传头像为空
	 * @return
	 */
	StringBuffer uploadUserFace(StringBuffer newUserFace, StringBuffer httpUrl, String oldUserFace);

}
