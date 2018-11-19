package com.primeton.liuzhichao.ums.application.service;

import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.primeton.liuzhichao.ums.application.dao.IUserMapper;
import com.primeton.liuzhichao.ums.application.entity.PageInfoUser;
import com.primeton.liuzhichao.ums.application.entity.ResultEnum;
import com.primeton.liuzhichao.ums.application.entity.User;
import com.primeton.liuzhichao.ums.application.entity.UserAndOrg;
import com.primeton.liuzhichao.ums.application.exception.DemoException;

/**
 * 员工管理业务层实现类，负责具体的业务逻辑
 * @author ASUS
 *
 */
@Service
public class UserServiceImpl implements IUserService{

	@Autowired
	private IUserMapper userMapper;

	/**
	 * 注册新用户
	 */
	@Override
	public User createUser(User user)  throws DemoException{
		//调用getEmpByEmpName方法查询用户，数据库中无此用户则可以注册，有此用户则注册失败
		User data = getUserByUserName(user.getUserName());
		if(data == null) {
			//封装日志
			Date now = new Date();
			user.setCreationName(user.getUserName());
			user.setCreationTime(now);
			user.setModifier(user.getUserName());
			user.setModifyTime(now);
			//可以注册,调用addUser()方法插入数据
			userMapper.insertUser(user);
			return user;
		}else {
			//注册失败
			throw new  DemoException(ResultEnum.ERROR_USERNAME);
		}
	}

	/**
	 * 用户登录功能
	 */
	@Override
	public User login(String userName, String userPassword) throws DemoException {
		//根据用户输入的用户名查找用户数据
		User data = getUserByUserName(userName);
		if(data != null) {
			if(data.getUserPassword().equals(userPassword)) {
				//登录成功
				return data;
			}else {
				//密码错误
				throw new DemoException(ResultEnum.ERROR_PWD_FORMT);
			}
		}else {
			//用户名错误
			throw new DemoException(ResultEnum.ERROR_NAME_FORMAT);
		}
	}

	/**
	 * 根据用户名修改用户密码
	 */
	@Override
	public Integer modifyPassword(Integer id, String oldPassword, String newPassword)
			throws DemoException {
		//根据用户id获取用户信息
		User user = getUserById(id);
		if(user==null) {
			//用户名不存在
			throw new DemoException(ResultEnum.ERROR_NAME_FORMAT);
		}
		Integer result = null;
		//进行原密码验证
		if(user.getUserPassword().equals (oldPassword)) {
			//密码匹配成功，可以修改密码
			result = userMapper.updatePassword(id,newPassword,user.getUserName(),new Date());
			if(result!=1) {
				//密码修改失败
				throw new  DemoException(ResultEnum.ERROR_PWD_MODIFY);
			}
		}else {
			//原密码验证失败
			throw new DemoException(ResultEnum.ERROR_PWD_VALIDATA);
		}
		return result;
	}
	/**
	 * 查询用户列表
	 */
	@Override
	public PageInfoUser queryUsers(Integer pageNum, Integer pageSize) {
		//查询总数据量
		Integer count = userMapper.getTotalCount();
		PageHelper.startPage(pageNum, pageSize);
		List<UserAndOrg> listUser = userMapper.queryUsers();
		PageInfoUser pageInfoUser = new PageInfoUser(count,listUser);
		return pageInfoUser;
	}
	/**
	 * 删除用户功能
	 */
	@Override
	@Transactional
	public Integer removeUserByIds(Integer[] ids,Integer id) throws DemoException{
		Integer rows = null;
		//判断权限
		if(getJobById(id)) {
			System.out.println("quanxian:"+getJobById(id));
			if(ids.length != 0) {
				//可以删除
				for(Integer i : ids) {
					//调用queryCountById()方法查看此用户有无下级员工
					Integer count = queryCountById(i);
					System.out.println("quanxian2:"+count);
					if(count == 0) {
						//此用户没有下级员工
						//调用dao层方法删除用户
						rows = userMapper.deleteUser(i);
					}else {
						//抛出此用户不可删除异常
						throw new DemoException(ResultEnum.ERROR_ID_DELETE);
					}
				}
			}else {
				//没有选中被删除的用户
				throw new DemoException(ResultEnum.ERROR_ID_EMPTY);
			}
		}else {
			//权限不足
			throw new DemoException(ResultEnum.ERROR_POWER_LOW);
		}
		return rows;
	}

	/**
	 * 根据用户名查询用户对象数据
	 */
	public User getUserByUserName(String userName) {
		return userMapper.getUserByUserName(userName);
	}
	
	/**
	 * 根据员工编号查询用户信息
	 */
	public UserAndOrg getUserByUserId(String userId) {
		return userMapper.getUserByUserId(userId);
		
	}

	/**
	 * 根据id获取用户信息
	 */
	public User getUserById(Integer id) {
		User user = userMapper.getUserById(id);
		return user;
	}

	/**
	 * 查询此用户下级员工数量
	 */
	@Override
	public Integer queryCountById(Integer id) {
		//根据id查询用户信息
		User user = getUserById(id);
		System.out.println("user:"+user);
		Integer count = userMapper.getCountByMrgId(user.getUserId());
		return count;
	}

	/**
	 * 根据用户id判断权限
	 * @param session
	 * @return true代表权限达标，false代表权限太低
	 */
	public Boolean getJobById(Integer id) {
		//根据id查询用户的信息(职位)
		System.out.println("开始100，"+id);
		User user = getUserById(id);
		//判断权限
		if("董事长".equals(user.getJob())) {
			return true;
		}else {
			return false;
		}
	}


	
}
