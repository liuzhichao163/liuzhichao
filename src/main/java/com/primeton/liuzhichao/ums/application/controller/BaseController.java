package com.primeton.liuzhichao.ums.application.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * 项目中所有控制器类的基类
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
