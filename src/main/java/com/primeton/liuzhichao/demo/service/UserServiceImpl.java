package com.primeton.liuzhichao.demo.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.primeton.liuzhichao.demo.dao.IUserMapper;
import com.primeton.liuzhichao.demo.entity.PageInfoUser;
import com.primeton.liuzhichao.demo.entity.User;
import com.primeton.liuzhichao.demo.entity.UserAndOrg;
import com.primeton.liuzhichao.demo.exception.DemoException;
import com.primeton.liuzhichao.demo.exception.ExceptionEnum;
import com.primeton.liuzhichao.demo.utils.PoiUtils;
import com.primeton.liuzhichao.demo.utils.Utils;

/**
 * 用户Service层Api
 * 
 * @author ASUS
 *
 */
@Service
public class UserServiceImpl implements IUserService,UserDetailsService {

	@Autowired
	private IUserMapper userMapper;
	
	/**
	 * SpringSecurity框架，根据登录的用户名查询用户信息（包含当前用户的角色）
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userMapper.getUserByName2(username);
		if(user == null) {
			throw new DemoException(ExceptionEnum.ERROR_NAME_FORMAT);
		}
		return user;
	}
	
	
	/**
	 * 注册新用户
	 */
	@Override
	public UserAndOrg createUser(User user){
		System.out.println("======"+JSON.toJSONString(user));
		UserAndOrg data = userMapper.getUserByName(user.getName());
		if (data == null) {
			// 可以注册,调用addUser()方法插入数据
			userMapper.insertUser(user);
			return data;
		} else {
			// 注册失败
			throw new DemoException(ExceptionEnum.ERROR_USERNAME);
		}
	}

	/**
	 * 删除用户功能
	 */
	@Override
	@Transactional
	public Integer removeUser(Integer id){
		Integer rows = null;
		User user = userMapper.getUser(id);
		if (user != null) {
			// 调用dao层方法删除用户
			rows = userMapper.deleteUser(id);
		} else {
			// 没有选中被删除的用户
			throw new DemoException(ExceptionEnum.ERROR_ID_EMPTY);
		}
		return rows;
	}

	/**
	 * 根据id修改用户密码
	 */
	@Override
	public Integer modifyPassword(Integer id, String oldPassword, String newPassword){
		// 根据用户id获取用户信息
		User user = userMapper.getUser(id);
		if (user == null) {
			// 用户名不存在
			throw new DemoException(ExceptionEnum.ERROR_NAME_FORMAT);
		}
		Integer result = null;
		// 进行原密码验证
		if (user.getPassword().equals(oldPassword)) {
			// 密码匹配成功，可以修改密码
			result = userMapper.updatePassword(id, newPassword);
			if (result != 1) {
				// 密码修改失败
				throw new DemoException(ExceptionEnum.ERROR_PWD_MODIFY);
			}
		} else {
			// 原密码验证失败
			throw new DemoException(ExceptionEnum.ERROR_PWD_VALIDATA);
		}
		return result;
	}

	/**
	 * 根据用户id修改用户信息
	 * 
	 * @throws DemoException 修改信息失败抛出异常
	 */
	@Override
	public Integer modifyUser(User user){
		Integer rows = userMapper.updateUser(user.getId(), user.getJob(), user.getMgrId(), user.getOrgId());
		if (rows != 1) {
			// 修改失败
			throw new DemoException(ExceptionEnum.ERROR_USER_MODIFY);
		}
		return rows;
	}

	/**
	 * 用户登录功能
	 */
	@Override
	public UserAndOrg login(String userName, String userPassword){
		UserAndOrg data = userMapper.getUserByName(userName);
		if (data != null) {
			if (data.getUserPassword().equals(userPassword)) {
				// 登录成功
				return data;
			} else {
				// 密码错误
				throw new DemoException(ExceptionEnum.ERROR_PWD_FORMT);
			}
		} else {
			// 用户名错误
			throw new DemoException(ExceptionEnum.ERROR_NAME_FORMAT);
		}
	}
	
	
	/**
	 * 根据用户名模糊查询查询用户对象数据
	 */
	public PageInfoUser getUserByName(String userName,Integer pageNum,Integer pageSize) {
		String userNames = "%"+userName+"%";
		Integer count = userMapper.getTotalCountByFuzzy(userNames);
		PageHelper.startPage(pageNum, pageSize);
		List<UserAndOrg> userAndOrg = userMapper.getUserByFuzzy(userNames);
		PageInfoUser pageInfoUser = new PageInfoUser(count,userAndOrg);
		return pageInfoUser;
	}

	/**
	 * 查询用户列表
	 */
	@Override
	public PageInfoUser queryUsers(Integer pageNum, Integer pageSize) {
		// 查询总数据量
		Integer count = userMapper.getTotalCount();
		PageHelper.startPage(pageNum, pageSize);
		List<UserAndOrg> UserAndOrg = userMapper.queryUsers();
		PageInfoUser pageInfoUser = new PageInfoUser(count,UserAndOrg);
		return pageInfoUser;
	}
	
	/**
	 * 导出员工信息到excel
	 */
	@Override
	public ResponseEntity<byte[]> exportEmp() {
		//查询所有的员工信息
		List<UserAndOrg> UserAndOrg = userMapper.queryUsers();
		//excel表的表头内容
		String[] headerName = {"员工ID","员工姓名","职位","所属部门","部门地址"};
		//cxcel单元格的宽度
		int[] CellWidth = {10,15,15,15,20};
		//将员工信息对象数组转换成ArrayLlist(有序)
		List<List<String>> list = new ArrayList<List<String>>();
		for(int i=0; i<UserAndOrg.size(); i++) {
			List<String> contentList = new ArrayList<String>();
			contentList.add(UserAndOrg.get(i).getUserId());
			contentList.add(UserAndOrg.get(i).getUserName());
			contentList.add(UserAndOrg.get(i).getJob());
			contentList.add(UserAndOrg.get(i).getOrgName());
			contentList.add(UserAndOrg.get(i).getOrgLoc());
			list.add(contentList);
		}
		return PoiUtils.exportExcel(list, headerName, CellWidth);
	}


	@Override
//	@Transactional
	public StringBuffer uploadUserFace(StringBuffer newUserFace, StringBuffer httpUrl, String oldUserFace) {
			Utils.deleteFile(oldUserFace);
			String userId = Utils.getCurrentUser().getUserId();
			Integer row = userMapper.updateUserFace(newUserFace.toString(),httpUrl.toString(), userId);
			if(row != 1) {
				throw new DemoException(ExceptionEnum.UNKONW_ERROR);
			}
		return newUserFace;
	}
	
	

	

}
