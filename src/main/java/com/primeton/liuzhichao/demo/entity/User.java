package com.primeton.liuzhichao.demo.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import io.swagger.annotations.ApiModel;
import lombok.ToString;

/**
 * 用户对象实体类
 * 
 * @author ASUS
 *
 */
@ApiModel(value = "用户对象", description = "员工对象User")
//@Data
@ToString
public class User implements UserDetails, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6438122922334303265L;
	private Integer id;
	private String userId;
	private String name;
	private String password;
	private String job;
	private String mgrId;
	private String orgId;
	private String newPassword;
	private String oldPassword;
	private String userFace;
	private String httpUserFace;
	private List<Role> roles;

	public User() {
		super();
	}

	public User(Integer id, String userId, String name, String password, String job, String mgrId, String orgId,
			String newPassword, String oldPassword, String userFace,String httpUserFace, List<Role> roles) {
		super();
		this.id = id;
		this.userId = userId;
		this.name = name;
		this.password = password;
		this.job = job;
		this.mgrId = mgrId;
		this.orgId = orgId;
		this.newPassword = newPassword;
		this.oldPassword = oldPassword;
		this.userFace = userFace;
		this.httpUserFace = httpUserFace;
		this.roles = roles;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authoritys = new ArrayList<GrantedAuthority>();
		for (Role role : roles) {
			authoritys.add(new SimpleGrantedAuthority(role.getName()));
		}
		return authoritys;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getMgrId() {
		return mgrId;
	}

	public void setMgrId(String mgrId) {
		this.mgrId = mgrId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserFace() {
		return userFace;
	}

	public void setUserFace(String userFace) {
		this.userFace = userFace;
	}

	public String getHttpUserFace() {
		return httpUserFace;
	}

	public void setHttpUserFace(String httpUserFace) {
		this.httpUserFace = httpUserFace;
	}



}
