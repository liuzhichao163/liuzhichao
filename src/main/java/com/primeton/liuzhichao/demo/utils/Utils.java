package com.primeton.liuzhichao.demo.utils;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import com.primeton.liuzhichao.demo.entity.User;

public class Utils {
	/**
	 * 获取当前登录用户对象
	 * @return
	 */
	public static User getCurrentUser() {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return user;
	}
	
	/**
	 * 删除文件
	 * @param filePath 文件路径
	 * @return
	 */
	public static Boolean deleteFile(String filePath) {
		File file = new File(filePath);
		Boolean flag = true;
//		Boolean a = file.exists();
		
		if(StringUtils.isNotBlank(filePath) && file.exists()) {
			flag = file.delete();
			System.out.println("是否删除："+flag+"---"+filePath);
		}
		return flag;
	}
}
