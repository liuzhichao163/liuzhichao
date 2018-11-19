package com.primeton.liuzhichao.ums.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.primeton.liuzhichao.ums.application.dao.IOrgMapper;
import com.primeton.liuzhichao.ums.application.entity.Org;
import com.primeton.liuzhichao.ums.application.entity.PageInfoUser;
import com.primeton.liuzhichao.ums.application.entity.ResultEnum;
import com.primeton.liuzhichao.ums.application.entity.UserAndOrg;
import com.primeton.liuzhichao.ums.application.exception.DemoException;
/**
 * 部门管理控制层Service实现类
 * @author ASUS
 *
 */
@Service
public class OrgServiceImpl implements IOrgService{

	//声明部门dao层对象
	@Autowired
	private IOrgMapper orgMapper;
	//声明用户dao层对象
	@Autowired
	private IUserService userService;

	/**
	 * 添加部门
	 */
	@Override
	public Org createOrg(Org org) throws DemoException {
		//判断此部门是否已存在
		if(getOrgByName(org.getOrgName()) == null) {
			System.out.println("org:"+org.toString());
			orgMapper.insertOrg(org);
			return org;
		}else {
			//添加失败
			throw new DemoException(ResultEnum.ERROR_DEPT_NAME);
		}
	}

	/**
	 * 根据部门名称查询部门信息
	 */
	@Override
	public Org getOrgByName(String orgName) {
		return orgMapper.getOrgByName(orgName);
	}

	/**
	 * 根据部门id删除部门
	 */
	@Override
	@Transactional
	public Integer removeOrgByOrgId(String orgId,Integer id) throws DemoException{
		//判断权限
		if(userService.getJobById(id)) {
			//可以删除部门,判断此部门是否有子部门
			if(getCountByOrgId(orgId) == 0) {
					//删除员工
					orgMapper.deleteUserByOrgId(orgId);
					//删除此部门
					Integer date = orgMapper.deleteOrgByOrgId(orgId);
					if(date == 0) {
						//删除部门失败
						throw new DemoException(ResultEnum.ERROR_DEPT_FAILURE);
					}
					return date;
			}else {
				//抛出不可删除异常(含有子集部门)
				throw new DemoException(ResultEnum.ERROR_DEPT_DELETE);
			}
		}else {
			//抛出权限不够异常
			throw new DemoException(ResultEnum.ERROR_POWER_LOW);
		}
	}

	
	/**
	 * 根据部门id查询子集部门
	 * @return
	 */
	@Override
	public List<Org> queryOrgByOrgId(String orgId) throws DemoException{
		List<Org> orgs = orgMapper.queryOrgByOrgId(orgId);
		if(orgs.isEmpty()) {
			//部门无下级部门
			throw new DemoException(ResultEnum.ERROR_DEPT_LOWER);
		}
		return orgs;
	}
	
	/**
	 * 根据部门编号查询下级部门数量
	 * @return
	 */
	public Integer getCountByOrgId(String orgId) {
		return orgMapper.getCountByPid(orgId);
	}
	
	/**
	 * 根据部门编号查询部门员工信息
	 */
	@Override
	public PageInfoUser queryUserByOrgId(String orgId,Integer pageNum,Integer pageSize) throws DemoException{
		//查询此部门员工数量
		Integer count = orgMapper.getCountUserByOrgId(orgId);
		if(count == 0) {
			//此部门没有员工
			throw new DemoException(ResultEnum.ERROR_DEPT_NOEMP);
		}
		PageHelper.startPage(pageNum, pageSize);
		List<UserAndOrg> userAndOrg = orgMapper.queryUserByOrgId(orgId);
		PageInfoUser page = new PageInfoUser(count,userAndOrg);
		return page;
	}
}
