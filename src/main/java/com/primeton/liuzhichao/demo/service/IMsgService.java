package com.primeton.liuzhichao.demo.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.primeton.liuzhichao.demo.entity.MsgContent;
import com.primeton.liuzhichao.demo.entity.SysMsg;
import com.primeton.liuzhichao.demo.entity.User;
import com.primeton.liuzhichao.demo.entity.UserAndOrg;

/**
 * 消息处理逻辑类
 * @author ASUS
 *
 */
public interface IMsgService {
	
	/**
	 * 系统消息入库
	 * @param msg
	 * @return
	 */
	boolean addMsg(MsgContent msg); 
	
	/**
	 * 查询系统消息列表
	 * @param page  第几页
	 * @param size  每页共多少条
	 * @return      mybatis分页对象
	 */
	PageInfo<SysMsg> queryMsgs(Integer page,Integer size);
	
	/**
	 * 修改系统消息的状态
	 * @param flage  -1：'全部已读'按钮 ; 非-1：单个点击消息时的状态
	 * @param uid    当前用户的id
	 * @return      
	 */
	Boolean updataMsgState(Long flage, Long uid);
	
	/**
	 * 查询所有用户列表
	 * @return
	 */
	List<UserAndOrg> getUsers();

}
