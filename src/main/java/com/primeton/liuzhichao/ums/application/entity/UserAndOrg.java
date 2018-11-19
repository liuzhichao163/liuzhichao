package com.primeton.liuzhichao.ums.application.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 部门表和员工表的实体类
 * 
 * @author ASUS
 *
 */
public class UserAndOrg implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4257346147451064256L;
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
	private String orgName;
	private String orgLoc;
	private String pId;

	public UserAndOrg() {
		super();
	}

	public UserAndOrg(Integer id, String userId, String userName, String userPassword, String job, String mgrId,
			String orgId, String creationName, Date creationTime, String modifier, Date modifyTime, String orgName,
			String orgLoc, String pId) {
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
		this.orgName = orgName;
		this.orgLoc = orgLoc;
		this.pId = pId;
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

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgLoc() {
		return orgLoc;
	}

	public void setOrgLoc(String orgLoc) {
		this.orgLoc = orgLoc;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "UserAndOrg [id=" + id + ", userId=" + userId + ", userName=" + userName + ", userPassword="
				+ userPassword + ", job=" + job + ", mgrId=" + mgrId + ", orgId=" + orgId + ", creationName="
				+ creationName + ", creationTime=" + creationTime + ", modifier=" + modifier + ", modifyTime="
				+ modifyTime + ", orgName=" + orgName + ", orgLoc=" + orgLoc + ", pId=" + pId + "]";
	}

}
