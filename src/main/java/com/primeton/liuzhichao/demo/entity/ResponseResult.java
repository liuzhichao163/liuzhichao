package com.primeton.liuzhichao.demo.entity;

import com.primeton.liuzhichao.demo.exception.ExceptionEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 返回json字符串对象
 * 
 * @author ASUS
 *
 * @param <T>
 */
@ApiModel(description = "返回响应数据")
public class ResponseResult<T> {
	@ApiModelProperty(value = "错误编号")
	private Integer state;
	@ApiModelProperty(value = "错误信息")
	private String message;
	@ApiModelProperty(value = "返回对象")
	private T data;

	public ResponseResult() {
		super();
	}

	public ResponseResult(Integer state) {
		super();
		setState(state);
	}

	public ResponseResult(ExceptionEnum exceptionEnum) {
		super();
		this.message = exceptionEnum.getMsg();
		this.state = exceptionEnum.getCode();
	}
	

	public ResponseResult(ExceptionEnum exceptionEnum, Object data) {
		super();
		this.message = exceptionEnum.getMsg();
		this.state = exceptionEnum.getCode();
		this.data = (T) data;
	}

	public ResponseResult(Exception e) {
		super();

	}

	public ResponseResult(Integer state, String message) {
		super();
		setState(state);
		setMessage(message);
	}
	
	

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ResponseResult [state=" + state + ", message=" + message + ", data=" + data + "]";
	}

}
