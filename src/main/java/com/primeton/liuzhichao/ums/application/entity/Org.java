package com.primeton.liuzhichao.ums.application.entity;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 部门表实体类
 * 
 * @author ASUS
 *
 */
@ApiModel(value = "部门对象", description = "部门对象Org")
public class Org implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1041525175335927486L;
	private String orgId;
	private String orgName;
	private String orgLoc;
	private String pId;

	public Org() {
		super();
	}

	public Org(String orgId, String orgName, String orgLoc, String pId) {
		super();
		this.orgId = orgId;
		this.orgName = orgName;
		this.orgLoc = orgLoc;
		this.pId = pId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
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
		return "Org [orgId=" + orgId + ", orgName=" + orgName + ", orgLoc=" + orgLoc + ", pId=" + pId + "]";
	}

}
