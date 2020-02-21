package com.primeton.liuzhichao.demo.securityconfig;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class UrlAccessDecisionManager implements AccessDecisionManager{
	
	//判断当前登录用户所具有的权限是否在所访问路径权限集合中
	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		
		Iterator<ConfigAttribute> iterator = configAttributes.iterator();
		while(iterator.hasNext()) {
			ConfigAttribute configAttribute = iterator.next();
			String needRole = configAttribute.getAttribute();
			if("ROLE_LOGIN".equals(needRole)) {
				//判断是否登录
				if(authentication instanceof AnonymousAuthenticationToken) {
					//抛出凭证无效异常
					throw new BadCredentialsException("未登陆");
				}else {
					//放行
					return;
				}
			}
			
			//当前登录用户所具有的权限集合
			Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
			//认证方法（可根据业务需求修改）
			for(GrantedAuthority authoritie : authorities) {
				if(authoritie.getAuthority().equals(needRole)) {
					return;
				}
			}
		}
		
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

}
