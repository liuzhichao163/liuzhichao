package com.primeton.liuzhichao.demo.entity;

import java.util.List;

/**
 * 分页查询数据对象
 * 
 * @author ASUS
 *
 */

public class PageInfoUser {
	// 总的数据条数
	private Integer count;
	// 每次查询数据集合
	private List<UserAndOrg> userAndOrg;

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

	@Override
	public String toString() {
		return "PageInfoUser [count=" + count + ", userAndOrg=" + userAndOrg + "]";
	}

}
