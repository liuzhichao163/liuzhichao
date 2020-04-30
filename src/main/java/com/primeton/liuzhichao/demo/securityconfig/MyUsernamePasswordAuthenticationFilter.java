package com.primeton.liuzhichao.demo.securityconfig;

import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 重写用户登录接口，将登录接口改为json格式
 * @author ASUS
 *
 */
//@Component
public class MyUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	@SuppressWarnings("finally")
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		String s = request.getContentType();
		String m =  MediaType.APPLICATION_JSON_UTF8_VALUE;
		String l = MediaType.APPLICATION_JSON_VALUE;
		if(request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE) || request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
			//JACKSON类中的对象，用于json和对象之间的转化
			ObjectMapper om = new ObjectMapper();
			UsernamePasswordAuthenticationToken authRequest = null;
			
			//InputStream is = request.getInputStream()获取post请求的数据
			try(InputStream in = request.getInputStream()){
				//利用jackson的ObjectMapper的readValue方法将post请求中的json串转换成指定类型，这里转换成了Map
				Map<String,String> authenticationBean = om.readValue(in, Map.class);
				authRequest = new UsernamePasswordAuthenticationToken(authenticationBean.get("username"), authenticationBean.get("password"));
			}catch (Exception e) {
				e.printStackTrace();
				authRequest = new UsernamePasswordAuthenticationToken("", "");
			}finally {
				 setDetails(request, authRequest);
			     return this.getAuthenticationManager().authenticate(authRequest);
			}
		}else {
			return super.attemptAuthentication(request, response);
		}
		
	}
	
	
}
