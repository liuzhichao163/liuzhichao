package com.primeton.liuzhichao.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.primeton.liuzhichao.demo.dao.IMsgMapper;
import com.primeton.liuzhichao.demo.dao.IUserMapper;
import com.primeton.liuzhichao.demo.entity.MsgContent;
import com.primeton.liuzhichao.demo.entity.SysMsg;
import com.primeton.liuzhichao.demo.entity.User;
import com.primeton.liuzhichao.demo.entity.UserAndOrg;

@Service
public class MsgServiceImpl implements IMsgService{
	
	@Autowired
	IMsgMapper msgMapper;
	
	@Autowired
	IUserMapper userMapper;
	
	@Override
	public boolean addMsg(MsgContent msg) {
		//先将消息入库
		int result = msgMapper.sendMsg(msg);
		//再查询出所有人员
		List<UserAndOrg> users = userMapper.queryUsers();
		//将人员id和消息id入库,返回受影响行数
		int result2 = msgMapper.addSysMsg(users, msg.getId());
		//将受影响行数和所有人员数对比，一致的话就返回true，入库成功
		return result2 == users.size();
	}

	@Override
	public PageInfo<SysMsg> queryMsgs(Integer page, Integer size) {
		PageHelper.startPage(page, size);
		List<SysMsg> list = msgMapper.queryMsgs();
		PageInfo<SysMsg> pageInfo = new PageInfo<SysMsg>(list);
		return pageInfo;
	}

	@Override
	public Boolean updataMsgState(Long flage, Long uid) {
		if(flage == -1) {
			//'全部设置已读'按钮
			msgMapper.updateMsgState(flage, uid);
			return true;
		}else {
			//单个点击消息时的状态
			return msgMapper.updateMsgState(flage, uid) == 1;
		}
		
	}

	@Override
	public List<UserAndOrg> getUsers() {
		List<UserAndOrg> users =  userMapper.queryUsers();
		return users;
	}

}
