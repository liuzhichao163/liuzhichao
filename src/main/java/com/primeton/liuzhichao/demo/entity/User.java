package com.primeton.liuzhichao.demo.entity;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;

/**
 * 用户对象实体类
 * 
 * @author ASUS
 *
 */
@ApiModel(value = "用户对象", description = "员工对象User")

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6883723160994808750L;
	private Integer id;
	private String userId;
	private String userName;
	private String userPassword;
	private String job;
	private String mgrId;
	private String orgId;
	private String newPassword;
	private String oldPassword;

	public User() {
		super();
	}

	public User(Integer id, String userId, String userName, String userPassword, String job, String mgrId, String orgId,
			String newPassword, String oldPassword) {
		super();
		this.id = id;
		this.userId = userId;
		this.userName = userName;
		this.userPassword = userPassword;
		this.job = job;
		this.mgrId = mgrId;
		this.orgId = orgId;
		this.newPassword = newPassword;
		this.oldPassword = oldPassword;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userId=" + userId + ", userName=" + userName + ", userPassword=" + userPassword
				+ ", job=" + job + ", mgrId=" + mgrId + ", orgId=" + orgId + ", newPassword=" + newPassword
				+ ", oldPassword=" + oldPassword + "]";
	}

}
