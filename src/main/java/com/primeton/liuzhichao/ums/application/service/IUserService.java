package com.primeton.liuzhichao.ums.application.service;



import com.primeton.liuzhichao.ums.application.entity.PageInfoUser;
import com.primeton.liuzhichao.ums.application.entity.User;
import com.primeton.liuzhichao.ums.application.entity.UserAndOrg;
import com.primeton.liuzhichao.ums.application.exception.DemoException;


/**
 * 员工管理业务层借口
 * @author ASUS
 *
 */

public interface IUserService {
	/**
	 * 注册功能
	 * @param user 用户数据对象
	 * @return 返回注册成功的用户对象，包含用户id
	 * @throws DemoException 注册失败抛出DemoException异常
	 */
	User createUser(User user) throws DemoException;
	
	/**
	 * 登录功能
	 * @param userName 用户名
	 * @param empPassword 用户密码
	 * @throws DemoException  密码不正确抛出异常 用户名不正确抛出异常
	 * @return 用户数据对象
	 */
	User login(String userName,String userPassword)throws DemoException;

	/**
	 * 根据用户名修改用户密码
	 * @param id 用户登录后保存在session中的id
	 * @param oldPassword  数据库中存在的旧密码
	 * @param newPassword  新密码
	 * @throws DemoException  密码修改失败
	 * @return 返回生效行数
	 */
	Integer modifyPassword(Integer id,String oldPassword,String newPassword)
			throws DemoException;
	
	/**
	 * 根据用户名查询用户数据对象
	 * @param userName 用户名
	 * @return 用户数据对象，没有此用户则返回null
	 */
	User getUserByUserName(String userName);
	
	/**
	 * 根据用户id获取用户信息
	 * @param id 用户id
	 * @return 返回用户数据对象
	 */
	User getUserById(Integer id);
	
	/**
	 * 查询所有用户列表
	 * @return 返回含有用户信息的集合
	 */
	PageInfoUser queryUsers(Integer pageNum, Integer pageSize);
	
	/**
	 * 根据用户编号查询用户信息
	 * @param empId 用户编号
	 */
	UserAndOrg getUserByUserId(String userId);
	
	/**
	 * 删除指定的用户信息，此功能是权限功能
	 * @param ids 被删除的用户id数组
	 * @param id 当前此用户id，用来判断权限
	 * @return 生效行数
	 * @throws DemoException 抛出此用户不可删除异常、没有选中被删除的用户、权限不足
	 */
	Integer removeUserByIds(Integer[] ids,Integer id)throws DemoException;
	
	/**
	 * 查询此用户下级员工数量
	 * @param id 用户id
	 * @return 下级员工数量
	 */
	Integer queryCountById(Integer id);
	
	/**
	 * 根据用户存在session的id查询职位判断权限
	 * @param Id 存在session的id
	 * @return true 权限达标，false权限不够
	 */
	Boolean getJobById(Integer id);
	
	
}
