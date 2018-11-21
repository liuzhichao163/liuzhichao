package com.primeton.liuzhichao.demo.controller;

import javax.servlet.http.HttpSession;


/**
 * 控制器类的基类
 * 
 * @author ASUS
 *
 */

public abstract class BaseController {

	/**
	 * 从Session中获取用户id
	 * 
	 * @param session HttpSession
	 * @return 用户的id
	 */
	protected final Integer getIdFromSession(HttpSession session) {
		Integer id = Integer.valueOf(session.getAttribute("id").toString());
		System.out.println(id);
		return id;
	}

}
