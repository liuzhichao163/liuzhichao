package com.primeton.liuzhichao.demo.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 分页查询数据对象
 * 
 * @author ASUS
 *
 */

public class PageInfoUser implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2194894088304689706L;
	// 总的数据条数
	private Integer count;
	// 每次查询数据集合
	private List<UserAndOrg> userAndOrg;
	private List<Org> orgList;

	public PageInfoUser() {
		super();
	}

	public PageInfoUser(Integer count, List<UserAndOrg> userAndOrg) {
		super();
		this.count = count;
		this.userAndOrg = userAndOrg;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public List<UserAndOrg> getUserAndOrg() {
		return userAndOrg;
	}

	public void setUserAndOrg(List<UserAndOrg> userAndOrg) {
		this.userAndOrg = userAndOrg;
	}

	public List<Org> getOrgList() {
		return orgList;
	}

	public void setOrgList(List<Org> orgList) {
		this.orgList = orgList;
	}

	@Override
	public String toString() {
		return "PageInfoUser [count=" + count + ", userAndOrg=" + userAndOrg + ", orgList=" + orgList + "]";
	}

	
}
