package com.primeton.liuzhichao.demo.config;

import java.io.IOException;

import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.primeton.liuzhichao.demo.entity.ResponseResult;
import com.primeton.liuzhichao.demo.exception.ExceptionEnum;

@Component
public class AuthenticationAccessDeniedHandler implements AccessDeniedHandler{

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse resp,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
	    resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        ResponseResult<Void> error = new ResponseResult<Void>(ExceptionEnum.ERROR_POWER_LOW);
        out.write(new ObjectMapper().writeValueAsString(error));
        out.flush();
        out.close();
		
	}

}
