package com.primeton.liuzhichao.ums.application.exception;

import com.primeton.liuzhichao.ums.application.entity.ResultEnum;
/**
 * 自定义异常
 * @author ASUS
 *
 */
public class DemoException extends RuntimeException {
	 private Integer code;

	 public DemoException(ResultEnum resultEnum) {
	        super(resultEnum.getMsg());
	        this.code = resultEnum.getCode();
	    }
	 
	    public Integer getCode() {
	        return code;
	    }

	    public void setCode(Integer code) {
	        this.code = code;
	    }
}
