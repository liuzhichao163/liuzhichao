package com.primeton.liuzhichao.demo.securityconfig;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.primeton.liuzhichao.demo.entity.ResponseResult;
import com.primeton.liuzhichao.demo.entity.TokenProperties;
import com.primeton.liuzhichao.demo.exception.ExceptionEnum;
import com.primeton.liuzhichao.demo.redis.JedisClient;
import com.primeton.liuzhichao.demo.redis.RedisLock;
import com.primeton.liuzhichao.demo.utils.JwtUtils;
import com.primeton.liuzhichao.demo.utils.MyBeanUtils;
import com.primeton.liuzhichao.demo.vo.UserVo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.primeton.liuzhichao.demo.entity.User;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter{

	protected JwtLoginFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
        setAuthenticationManager(authenticationManager);
    }

	//从请求中获取用户名和密码，调用AuthenticationManager.authenticate()方法进行校验
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		
		User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
		return getAuthenticationManager().authenticate(
				new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
	}
		
	/**
	 * 上面方法检验成功后,就会来到 successfulAuthentication 回调中，
	 * 在 successfulAuthentication 方法中，
	 * 将用户角色遍历然后用一个 , 连接起来，然后再利用 Jwts 去生成 token，
	 * 按照代码的顺序，生成过程一共配置了四个参数，
	 * 分别是用户角色、主题、过期时间以及加密算法和密钥，然后将生成的 token 写出到客户端。
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		 Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
		 StringBuffer as = new StringBuffer();
		 for (GrantedAuthority authority : authorities) {
	            as.append(authority.getAuthority()).append(",");
	        }

		JedisClient jedisClient = getDao(JedisClient.class,(HttpServletRequest)request);
		TokenProperties tokenProperties = getDao(TokenProperties.class,(HttpServletRequest)request);

		//创建并发送token
		JwtUtils.generatorJwtToken(MyBeanUtils.doToVo((User)authResult.getPrincipal(), UserVo.class),
									as,
									tokenProperties,
									response,
									jedisClient);
//		response.setContentType("application/json;chartset=utf-8");
//		ResponseResult<Void> responseResult = new ResponseResult<Void>(ExceptionEnum.SUCCESS);
//		PrintWriter pw = response.getWriter();
//		ObjectMapper om = new ObjectMapper();
//		pw.write(om.writeValueAsString(responseResult));
//		pw.flush();
//		pw.close();
	}

	//拦截器中无法通过@Autowired自动注入Bean，需要通过此方法获取Spring管理的Bean
	public <T> T getDao(Class<T> clazz,HttpServletRequest request){
		//通过该方法获得的applicationContext 已经是初始化之后的applicationContext 容器
		WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
		return applicationContext.getBean(clazz);
	}
	

}
