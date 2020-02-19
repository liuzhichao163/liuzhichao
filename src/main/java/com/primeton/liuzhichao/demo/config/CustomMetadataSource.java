package com.primeton.liuzhichao.demo.config;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.result.FlashAttributeResultMatchers;
import org.springframework.util.AntPathMatcher;

import com.primeton.liuzhichao.demo.entity.Menu;
import com.primeton.liuzhichao.demo.entity.Role;
import com.primeton.liuzhichao.demo.service.IMenuService;

@Component
public class CustomMetadataSource implements FilterInvocationSecurityMetadataSource{
	
	@Autowired
	IMenuService menuService;
	
	//spring的工具类，用于匹配路径：antPathMatcher.match(正则路径，url路径);
	AntPathMatcher antPathMatcher = new AntPathMatcher();
	
	/**
	 * 获取当前请求路径所具有的角色权限集合
	 */
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		//获取当前浏览器的请求地址
		String requestUrl = ((FilterInvocation) object).getRequestUrl();
		//获取数据库中的所有的urlPatth（包含与之对应的角色关系）
		List<Menu> menus = menuService.getMenus();
		for(Menu menu : menus) {
			if(antPathMatcher.match(menu.getUrl(), requestUrl) && menu.getRoles().size() > 0) {
				//获取该路径所具有的角色权限集合
				List<Role> roles = menu.getRoles();
				int size = roles.size();
				String[] values = new String[size];
				for(int i=0; i<size; i++) {
					values[i] = roles.get(i).getName();
				}
				//采用SecurityConfig.createList()创建一个角色集合
				return SecurityConfig.createList(values);
			}
		}
		//没有匹配上的资源，都是登录访问
        return SecurityConfig.createList("ROLE_LOGIN");
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

}
