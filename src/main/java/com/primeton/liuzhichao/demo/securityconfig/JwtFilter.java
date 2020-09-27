package com.primeton.liuzhichao.demo.securityconfig;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.primeton.liuzhichao.demo.config.RedisProperties;
import com.primeton.liuzhichao.demo.entity.ResponseResult;
import com.primeton.liuzhichao.demo.entity.TokenProperties;
import com.primeton.liuzhichao.demo.entity.User;
import com.primeton.liuzhichao.demo.redis.JedisClient;
import com.primeton.liuzhichao.demo.redis.JedisClientPool;
import com.primeton.liuzhichao.demo.redis.RedisLock;
import com.primeton.liuzhichao.demo.utils.JwtUtils;
import com.primeton.liuzhichao.demo.utils.MyBeanUtils;
import com.primeton.liuzhichao.demo.vo.UserVo;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;

import com.primeton.liuzhichao.demo.exception.DemoException;
import com.primeton.liuzhichao.demo.exception.ExceptionEnum;

/**
   jwt的组成部分
	1:header（头），保存算法，类型
	2:payload（负载），用户的信息，如id，用户名等等
	3:signature（签名），将生成的token编码（加密）;用于验证头部和荷载数据的完整性
	     他们之间用 "."号隔开。
 */

/**
 * 首先从请求头中提取出 authorization 字段，这个字段对应的 value 就是用户的 token。
         将提取出来的 token 字符串转换为一个 Claims 对象，再从 Claims 对象中提取出当前用户名和用户角色，
         创建一个 UsernamePasswordAuthenticationToken 放到当前的 Context 中，然后执行过滤链使请求继续执行下去。
 * @author ASUS
 *
 */

@Slf4j
public class JwtFilter extends GenericFilterBean {


	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		JwtUtils jwtUtils = getDao(JwtUtils.class,(HttpServletRequest)request);
		JedisClient jedisClient = getDao(JedisClient.class,(HttpServletRequest)request);
		RedisLock redisLock = getDao(RedisLock.class,(HttpServletRequest)request);
		TokenProperties tokenProperties = getDao(TokenProperties.class,(HttpServletRequest)request);


		HttpServletRequest req = (HttpServletRequest) request;
        String jwtToken = req.getHeader(tokenProperties.getAuthorizationHeaderName());
        System.out.println(jwtToken);
        try {
        	//解析token
			Claims claims = Jwts.parser().setSigningKey(tokenProperties.getSecretKey()).parseClaimsJws(jwtToken).getBody();
			String username = claims.getSubject();//获取当前登录用户名
			ObjectMapper om = new ObjectMapper();
			User user = om.convertValue(claims.get("user"),User.class);//获取当前用户
            String role = (String) claims.get(tokenProperties.getAuthorizationHeaderName()); //获取当前用户角色
			List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(role);
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, null, authorities);
			//将token填充到SecurityContextHolder中
			SecurityContextHolder.getContext().setAuthentication(token);

			String key = user.getUserId() + "_token";
			if(null == jedisClient.get(key)) { //刷新token
				try {
					if (redisLock.setLock(key)) { //获取到redis锁
							//创建并发送token
							jwtUtils.generatorJwtToken(
									MyBeanUtils.doToVo(user, UserVo.class),
									new StringBuffer(role),
									tokenProperties,
									(HttpServletResponse) response,
									jedisClient);
					}
				} finally {
					redisLock.unLock(key);
				}
			}
		} catch (Exception e) {
			logger.error(e);
			throw new DemoException(ExceptionEnum.ERROR_USER_LOGIN);
		}
        //传递到下一个过滤器，如果下面没有过滤器了，就传递到请求中
        chain.doFilter(req,response);
	}

	//拦截器中无法通过@Autowired自动注入Bean，需要通过此方法获取Spring管理的Bean
	public <T> T getDao(Class<T> clazz,HttpServletRequest request){
		//通过该方法获得的applicationContext 已经是初始化之后的applicationContext 容器
		WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
		return applicationContext.getBean(clazz);
	}



}
