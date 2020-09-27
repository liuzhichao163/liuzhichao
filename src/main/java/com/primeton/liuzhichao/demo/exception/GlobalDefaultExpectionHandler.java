package com.primeton.liuzhichao.demo.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.primeton.liuzhichao.demo.entity.ResponseResult;

/**
 * 统一异常处理
 * 此处不会捕获try/cach中抛出的异常
 * @author ASUS
 *
 */
@RestControllerAdvice
public class GlobalDefaultExpectionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalDefaultExpectionHandler.class);

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseResult<Void> handle(HttpServletRequest req, HttpServletResponse res, Exception e) {

		/**
		 * 自定义异常处理
		 */
		if (e instanceof DemoException) {
			logger.error("=======" + e.getMessage() + "=======");
			DemoException demoException = (DemoException) e;
			return new ResponseResult<Void>(demoException.getCode(), demoException.getMessage());
		} else {
			logger.error(e.getMessage(), e);
			return new ResponseResult<Void>(600, "服务器代码发生异常,请联系管理员");
		}

	}
}
