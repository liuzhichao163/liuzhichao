package com.primeton.liuzhichao.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.primeton.liuzhichao.demo.entity.MsgContent;
import com.primeton.liuzhichao.demo.entity.SysMsg;
import com.primeton.liuzhichao.demo.entity.User;
import com.primeton.liuzhichao.demo.entity.UserAndOrg;

@Mapper
public interface IMsgMapper {
	
	/**
	 * 将系统消息入库
	 * @param msg  消息对象
	 * @return  
	 */
	int sendMsg(MsgContent msg);
	
	/**
	 * 将消息、人员关联表数据入库
	 * @param userAndOrg
	 * @param mid
	 * @return
	 */
	int addSysMsg(@Param("userAndOrg") List<UserAndOrg> userAndOrg, @Param("mid") int mid);
	
	/**
	 * 查询系统消息列表
	 * @return 系统消息对象集合
	 */
	List<SysMsg> queryMsgs(Integer uid);
	
	/**
	 * 修改系统消息的状态
	 * @param flage  -1：'全部已读'按钮 ; 非-1：单个点击消息时的状态
	 * @param uid    当前用户的id
	 * @return       受影响的行数
	 */
	int updateMsgState(@Param("flage") Long flage, @Param("uid") Integer uid);
	
	/**
	 * 查询所有用户列表
	 * @return
	 */
	List<User> getUsers();

}
