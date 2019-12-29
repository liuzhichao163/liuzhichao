package com.primeton.liuzhichao.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.primeton.liuzhichao.demo.dao.IOrgMapper;
import com.primeton.liuzhichao.demo.entity.Org;
import com.primeton.liuzhichao.demo.entity.PageInfoUser;
import com.primeton.liuzhichao.demo.entity.UserAndOrg;
import com.primeton.liuzhichao.demo.exception.DemoException;
import com.primeton.liuzhichao.demo.exception.ExceptionEnum;

/**
 * 部门Service层Api
 * 
 * @author ASUS
 *
 */
@Service
public class OrgServiceImpl implements IOrgService {

	// 声明部门dao层对象
	@Autowired
	private IOrgMapper orgMapper;

	/**
	 * 添加部门
	 */
	@Override
	public Org createOrg(Org org){
		if (orgMapper.getOrgByName(org.getOrgName()).isEmpty()) {
			orgMapper.insertOrg(org);
			return org;
		} else {
			// 添加失败
			throw new DemoException(ExceptionEnum.ERROR_DEPT_NAME);
		}
	}

	/**
	 * 根据部门id删除部门
	 */
	@Override
	@Transactional
	public Integer removeOrg(String orgId) {
		if (getCount(orgId) == 0) {
			// 删除员工
			orgMapper.deleteUsers(orgId);
			// 删除此部门
			Integer date = orgMapper.deleteOrg(orgId);
			if (date == 0) {
				// 删除部门失败
				throw new DemoException(ExceptionEnum.ERROR_DEPT_FAILURE);
			}
			return date;
		} else {
			// 抛出不可删除异常(含有子集部门)
			throw new DemoException(ExceptionEnum.ERROR_DEPT_DELETE);
		}
	}
	
	/**
	 * 根据部门编号修改部门信息
	 */
	@Override
	public Integer modifyOrg(Org org) {
		Integer rows = orgMapper.updateOrg(org.getOrgId(),org.getOrgName(),org.getOrgLoc());
		return rows;
	}
	
	
	/**
	 * 根据部门id查询子集部门
	 * 
	 * @return
	 */
	@Override
	public List<Org> queryOrgs(String orgId){
		List<Org> orgs = orgMapper.queryOrgs(orgId);
		if (orgs.isEmpty()) {
			// 部门无下级部门
			throw new DemoException(ExceptionEnum.ERROR_DEPT_LOWER);
		}
		return orgs;
	}

	/**
	 * 根据部门编号查询下级部门数量
	 * 
	 * @return
	 */
	public Integer getCount(String orgId) {
		return orgMapper.getCount(orgId);
	}

	/**
	 * 根据部门编号查询部门员工信息
	 */
	@Override
	public PageInfoUser queryUsers(String orgId){
		Integer count = orgMapper.getUsersCount(orgId);
		if (count == 0) {
			// 此部门没有员工
			throw new DemoException(ExceptionEnum.ERROR_DEPT_NOEMP);
		}
		List<UserAndOrg> userAndOrg = orgMapper.queryUsers(orgId);
		PageInfoUser pageInfo = new PageInfoUser(count,userAndOrg);
		return pageInfo;
	}
	


	
	/**
	 * 分页查询所有部门信息
	 */
	@Override
	public PageInfoUser queryOrgs(String orgName,Integer pageNum,Integer pageSize) {
		PageInfoUser pageInfo = new PageInfoUser();
		if(pageNum==null && pageSize==null) {
			pageInfo.setOrgList(orgMapper.getOrgs());
			return pageInfo;
		}else {
			String orgNames = "%"+orgName+"%";
			Integer count = orgMapper.getTotalCountByFuzzy(orgNames);
			PageHelper.startPage(pageNum, pageSize);
			List<Org> orgs = orgMapper.getOrgByName(orgNames);
			 pageInfo.setCount(count);
			 pageInfo.setOrgList(orgs);
			return pageInfo;
		}
		
	}
	
	
	/**
	 * 根据部门名称查询部门信息
	 */
//	@Override
//	public PageInfoUser getOrgByName(String orgName,Integer pageNum,Integer pageSize) {
//		String orgNames = "%"+orgName+"%";
//		Integer count = orgMapper.getTotalCountByFuzzy(orgNames);
//		PageHelper.startPage(pageNum, pageSize);
//		List<Org> orgs = orgMapper.getOrgByName(orgNames);
//		 PageInfoUser pageInfo = new PageInfoUser();
//		 pageInfo.setCount(count);
//		 pageInfo.setOrg(orgs);
//		return pageInfo;
//	}


}
