package com.primeton.liuzhichao.demo.util;

/**
 * 验证器：用于验证字符串的格式是否正确
 * @author soft01
 *
 */
public class ValidatorUtil {
	
	private ValidatorUtil() {
		super();
	}
	
	/**
	 * 验证用户名的表达式
	 */
	private static final String REGEX_USERNAME = "^(?!_)(?!.*?_$)[a-zA-Z0-9_\\u4e00-\\u9fa5]+$";
	

	/**
	 * 验证密码的正则表达式
	 */
	public static final String REGEX_PASSWORD = "^\\d{6,18}$";
		
	/**
	 * 验证手机号的正则表达式
	 */
	public static final String REGEX_PHONE ="^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
	
	/**
	 * 验证邮箱的正则表达式
	 */
	
	public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\\\.)+[a-zA-Z]{2,}";
	
	
	/**
	 * 验证用户名的方法
	 * @param username 用户名
	 * @return 正确返回true；错误返回false。
	 */
	public static boolean checkUsername(String username) {
		
		return username.matches(REGEX_USERNAME);
	}
	
	/**
	 * 验证密码
	 * @param password 需要被验证格式的密码
	 * @return 如果符合格式要求，则返回true，否则返回false
	 */
	public static boolean checkPassword(String password) {
		return password.matches(REGEX_PASSWORD);
	}
	
	/**
	 * 验证手机号
	 * @param phone
	 * @return
	 */
	public static boolean checkphone(String phone) {
		return phone.matches(REGEX_PHONE);
	}
	
	/**
	 * 验证邮箱
	 * @param email
	 * @return
	 */
	public static boolean checkemail(String email) {
		return email.matches(REGEX_EMAIL);
	}
}
