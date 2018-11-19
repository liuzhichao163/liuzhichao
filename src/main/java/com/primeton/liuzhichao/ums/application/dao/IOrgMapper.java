package com.primeton.liuzhichao.ums.application.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.primeton.liuzhichao.ums.application.entity.Org;
import com.primeton.liuzhichao.ums.application.entity.UserAndOrg;

/**
 * dao层数据，负责与数据库交互部门表的信息
 * @author ASUS
 *
 */

@Mapper
public interface IOrgMapper {
	
	/**
	 * 添加部门
	 * @return 生效行数
	 */
	@Insert("INSERT INTO DEMO_ORG (ORG_ID,ORG_NAME,ORG_LOC,PID) "
			+ "VALUES(#{orgId},#{orgName},#{orgLoc},#{pId})")
	Integer insertOrg(Org org);
	
	
	/**
	 * 根据部门编号删除部门
	 * @param orgId
	 * @return
	 */
	@Delete("DELETE FROM DEMO_ORG WHERE ORG_ID = #{orgId} ")
	Integer deleteOrgByOrgId(String orgId);
	
	
	/**
	 * 根据部门名称查询部门信息
	 * @param orgName 部门名称
	 * @return 部门数据对象
	 */
	@Select("SELECT ORG_ID AS ORGID,ORG_NAME AS ORGNAME,ORG_LOC AS ORGLOC,PID AS PID "
			+ "FROM DEMO_ORG WHERE ORG_NAME = #{orgName} ")
	Org getOrgByName(String orgName);
	
	
	/**
	 * 根据部门编号查询有多少下级部门
	 * @param orgId 
	 * @return
	 */
	@Select("SELECT COUNT(*) FROM DEMO_ORG WHERE PID = #{orgId}")
	Integer getCountByPid(String orgId);
	
	/**
	 * 根据部门编号查询此部门员工数量
	 * @return
	 */
	@Select("SELECT COUNT(*) FROM DEMO_USER WHERE ORG_ID = #{orgId}")
	Integer getCountUserByOrgId(String orgId);
	
	/**
	 * 根据部门编号查询此部门员工信息
	 * @param orgId
	 * @return
	 */
	@Select("SELECT U.ID,U.USER_ID AS USERID,U.USER_NAME AS USERNAME,U.USER_PASSWORD AS USERPASSWORD,"
			+ "U.JOB AS JOB,U.MGR_ID AS MGRID,U.ORG_ID AS ORGID,U.CREATION_NAME AS CREATIONNAME,"
			+ "U.CREATION_TIME AS CREATIONTIME,"
			+ "U.MODIFIER AS MODIFIER,U.MODIFY_TIME AS MODIFYTIME,O.ORG_NAME AS ORGNAME,"
			+ "O.ORG_LOC AS ORGLOC,O.PID AS PID "
			+ "FROM DEMO_USER U RIGHT JOIN DEMO_ORG O ON U.ORG_ID = O.ORG_ID "
			+ "WHERE O.ORG_ID = #{orgId} ")
	List<UserAndOrg> queryUserByOrgId(String orgId);
	
	/**
	 * 根据部门编号查询下级部门信息
	 * @param  orgId 部门编号
	 * @return 下级部门集合信息
	 */
	@Select("SELECT ORG_ID AS ORGID,ORG_NAME AS ORGNAME,ORG_LOC AS ORGLOC,PID AS PID "
			+ "FROM DEMO_ORG WHERE PID =  #{orgId} ")
	List<Org> queryOrgByOrgId(String orgId);
	
	/**
	 * 根据部门编号删除员工
	 * @param orgId 部门编号
	 * @return 生效行数
	 */
	@Delete("DELETE FROM DEMO_USER WHERE ORG_ID = #{orgId}")
	Integer deleteUserByOrgId(String orgId);
	

}
