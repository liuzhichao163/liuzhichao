package com.primeton.liuzhichao.demo.securityconfig;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.util.DigestUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.primeton.liuzhichao.demo.entity.ResponseResult;
import com.primeton.liuzhichao.demo.entity.User;
import com.primeton.liuzhichao.demo.exception.ExceptionEnum;
import com.primeton.liuzhichao.demo.service.UserServiceImpl;
import com.primeton.liuzhichao.demo.utils.MD5Utils;
import com.primeton.liuzhichao.demo.utils.Utils;
/**
 * @EnableGlobalMethodSecurity(prePostEnabled = true) 使用表达式时间方法级别的安全性         4个注解可用
 * @PreAuthorize 在方法调用之前,基于表达式的计算结果来限制对方法的访问
 * @PostAuthorize 允许方法调用,但是如果表达式计算结果为false,将抛出一个安全性异常
 * @PostFilter 允许方法调用,但必须按照表达式来过滤方法的结果
 * @PreFilter 允许方法调用,但必须在进入方法之前过滤输入值
 * 
 * @author ASUS
 *
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserServiceImpl userService;

	@Autowired
	CustomMetadataSource metadataSource;

	@Autowired
	UrlAccessDecisionManager urlAccessDecisionManager;

	@Autowired
	AuthenticationAccessDeniedHandler deniedHandler;
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		 auth.userDetailsService(userService)
      		.passwordEncoder(passwordEncoder());
	}

	 //WEB安全
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/index.html", "/static/**", "/login_p", "/favicon.ico","/images/**");
    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.withObjectPostProcessor(objectPostProcessor())
				.and()
				.formLogin()
				.loginPage("/login_p")
				.loginProcessingUrl("/login")
				.usernameParameter("username")
				.passwordParameter("password")
				.failureHandler(authenticationFailureHandler())
				.successHandler(authenticationSuccessHandler())
				.permitAll()
				.and()
				.logout()
				.logoutUrl("/logout")
				.logoutSuccessHandler(logoutSuccessHandler())
				.permitAll()
				.and()
				.csrf()
				.disable()
				.exceptionHandling()
				.accessDeniedHandler(deniedHandler)
				.authenticationEntryPoint(authenticationEntryPoint());
	}

	// 用户未登录调用接口返回自定义信息
	private AuthenticationEntryPoint authenticationEntryPoint() {
		return new AuthenticationEntryPoint() {
			public void commence(HttpServletRequest request, HttpServletResponse response,
					AuthenticationException authException) throws IOException, ServletException {
				response.setContentType("text/html;charset=utf-8");
				ResponseResult<Void> responseResult = new ResponseResult<Void>(ExceptionEnum.ERROR_USER_LOGIN);
				ObjectMapper om = new ObjectMapper();
				PrintWriter pw = response.getWriter();
				pw.print(om.writeValueAsString(responseResult));
				pw.flush();
				pw.close();
			}
		};
	}
	//认证成功返回信息
	private AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new AuthenticationSuccessHandler() {
			// 重写认证成功返回信息
			@Override
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
					Authentication authentication) throws IOException, ServletException {

				response.setContentType("application/json;charset=utf-8");
				ResponseResult<T> responseResult = new ResponseResult<T>(ExceptionEnum.SUCCESS,Utils.getCurrentUser());
				PrintWriter pw = response.getWriter();
				ObjectMapper om = new ObjectMapper();
				pw.write(om.writeValueAsString(responseResult));
				pw.flush();
				pw.close();
			}
		};
	}
    
	//退出成功方法
	private LogoutSuccessHandler logoutSuccessHandler() {
		return new LogoutSuccessHandler() {
			@Override
			public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
					Authentication authentication) throws IOException, ServletException {

				response.setContentType("application/json;chartset=utf-8");
				ResponseResult<Void> responseResult = new ResponseResult<Void>(ExceptionEnum.SUCCESS);
				PrintWriter pw = response.getWriter();
				ObjectMapper om = new ObjectMapper();
				pw.write(om.writeValueAsString(responseResult));
				pw.flush();
				pw.close();

			}
		};
		
	}
	
	//认证失败返回信息
	private AuthenticationFailureHandler authenticationFailureHandler() {
		return new AuthenticationFailureHandler() {
		// 重写访问失败返回信息
		@Override
		public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
				AuthenticationException exception) throws IOException, ServletException {
	
				response.setContentType("application/json;charset=utf-8"); // 错误：此处将charset写成了chartset导致返回的msg为乱码
				ResponseResult<Void> responseResult = null;
				if (exception instanceof BadCredentialsException
						|| exception instanceof UsernameNotFoundException) {
					responseResult = new ResponseResult<Void>(ExceptionEnum.ERROR_PWD_FORMT);
				} else if (exception instanceof LockedException) {
					responseResult = new ResponseResult<Void>(ExceptionEnum.ERROR_ID_LOCK);
				} else if (exception instanceof CredentialsExpiredException) {
					responseResult = new ResponseResult<Void>(ExceptionEnum.ERROR_PWD_PASTDUE);
				} else if (exception instanceof AccountExpiredException) {
					responseResult = new ResponseResult<Void>(ExceptionEnum.ERROR_ID_PASTDUE);
				} else if (exception instanceof DisabledException) {
					responseResult = new ResponseResult<Void>(ExceptionEnum.ERROR_ID_DISABLED);
				} else {
					responseResult = new ResponseResult<Void>(ExceptionEnum.ERROR_LOGIN_FAILURE);
				}
	
				response.setStatus(401);
				ObjectMapper om = new ObjectMapper();
				PrintWriter pw = response.getWriter();
				pw.write(om.writeValueAsString(responseResult));
				pw.flush();
				pw.close();
	
			}
		};
	}
	
	//重写权限认证方法
	private ObjectPostProcessor<FilterSecurityInterceptor> objectPostProcessor() {
		return new ObjectPostProcessor<FilterSecurityInterceptor>() {

			@Override
			public <O extends FilterSecurityInterceptor> O postProcess(O object) {
				object.setSecurityMetadataSource(metadataSource);
				object.setAccessDecisionManager(urlAccessDecisionManager);
				return object;
			}
			
			
		};
		
	}
	
	//密码加密解密方法
	private PasswordEncoder passwordEncoder() {
		return new PasswordEncoder() {
			/**
			 * 对比验证密码
			 * rawPassword：要被验证的密码
			 * encodedPassword：数据库中的密码
			 */
			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				return encodedPassword.equals(MD5Utils.md5(rawPassword.toString()));
			}
			/**
			 * 进行将密码MD5加密
			 */
			@Override
			public String encode(CharSequence rawPassword) {
				return MD5Utils.md5(rawPassword.toString());
			}
		};
	}
}
