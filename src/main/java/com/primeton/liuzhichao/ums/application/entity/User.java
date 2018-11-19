package com.primeton.liuzhichao.ums.application.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.swagger.annotations.ApiModel;

/**
 * 用户对象实体类
 * 
 * @author ASUS
 *
 */
@ApiModel(value = "用户对象", description = "员工对象User")
@Component
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1209099961136592697L;
	private Integer id;
	private String userId;
	private String userName;
	private String userPassword;
	private String job;
	private String mgrId;
	private String orgId;
	private String creationName;
	private Date creationTime;
	private String modifier;
	private Date modifyTime;
	private String newOrgId;
	private String newPassword;

	public User() {
		super();
	}

	public User(Integer id, String userId, String userName, String userPassword, String job, String mgrId, String orgId,
			String creationName, Date creationTime, String modifier, Date modifyTime, String newOrgId,
			String newPassword) {
		super();
		this.id = id;
		this.userId = userId;
		this.userName = userName;
		this.userPassword = userPassword;
		this.job = job;
		this.mgrId = mgrId;
		this.orgId = orgId;
		this.creationName = creationName;
		this.creationTime = creationTime;
		this.modifier = modifier;
		this.modifyTime = modifyTime;
		this.newOrgId = newOrgId;
		this.newPassword = newPassword;
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

	public String getCreationName() {
		return creationName;
	}

	public void setCreationName(String creationName) {
		this.creationName = creationName;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getNewOrgId() {
		return newOrgId;
	}

	public void setNewOrgId(String newOrgId) {
		this.newOrgId = newOrgId;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userId=" + userId + ", userName=" + userName + ", userPassword=" + userPassword
				+ ", job=" + job + ", mgrId=" + mgrId + ", orgId=" + orgId + ", creationName=" + creationName
				+ ", creationTime=" + creationTime + ", modifier=" + modifier + ", modifyTime=" + modifyTime
				+ ", newOrgId=" + newOrgId + ", newPassword=" + newPassword + "]";
	}

}
