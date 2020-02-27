package com.primeton.liuzhichao.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.primeton.liuzhichao.demo.entity.MsgContent;
import com.primeton.liuzhichao.demo.entity.ResponseResult;
import com.primeton.liuzhichao.demo.entity.SysMsg;
import com.primeton.liuzhichao.demo.exception.ExceptionEnum;
import com.primeton.liuzhichao.demo.service.IMsgService;

@RestController
@RequestMapping("api/msg")
public class MsgController {
	
	@Autowired
	IMsgService msgService;
	
	/**
	 * 前端页面推送的系统消息
	 * @param msg  系统消息
	 * @return  推送是否成功
	 */
	@RequestMapping()
	public ResponseResult<Void> addMsg(@RequestBody MsgContent msg){
		System.out.println("======msg======"+msg.toString());
		if(msgService.addMsg(msg)) {
			System.out.println(new ResponseResult<Void>(ExceptionEnum.SUCCESS));
			return new ResponseResult<Void>(ExceptionEnum.SUCCESS);
		}else {
			return new ResponseResult<Void>(ExceptionEnum.ERROR_SYSMSG_FAILURE);
		}
	}
	/**
	 * 查询系统消息列表
	 * @param page  查询的页码
	 * @param size  每页的条数
	 * @return      mybatis分页对象
	 */
	@GetMapping()
	public PageInfo<SysMsg> queryMsgs(@RequestParam(value="page",defaultValue="1") Integer page,
							  @RequestParam(value="size",defaultValue="10") Integer size){
		
		return msgService.queryMsgs(page, size);
	}
	
	/**
	 * 修改系统消息的状态
	 * @param flage  -1：'全部设置为已读'， !-1:单个设置为已读
	 * @return      
	 */
	@PutMapping()
	public ResponseResult<Void> updateMsgState(Long flage){
		System.out.println("---------flage--------:"+flage);
		if(msgService.updataMsgState(flage, 50L)) {
			return new ResponseResult<Void>(ExceptionEnum.SUCCESS);
		}else {
			return new ResponseResult<Void>(ExceptionEnum.UNKONW_ERROR);
		}
	}

}
