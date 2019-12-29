package com.primeton.liuzhichao.demo.exception;

/**
 * 自定义异常
 * 
 * @author ASUS
 *
 */
public class DemoException extends RuntimeException  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3353102070086620237L;
	private Integer code;

	public DemoException(ExceptionEnum exceptionEnum) {
		super(exceptionEnum.getMsg());
		this.code = exceptionEnum.getCode();
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
}
