package com.primeton.liuzhichao.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.primeton.liuzhichao.demo.entity.Org;
import com.primeton.liuzhichao.demo.entity.UserAndOrg;

/**
 * dao层Api
 * 
 * @author ASUS
 *
 */

@Mapper
public interface IOrgMapper {

	/**
	 * 添加部门
	 * 
	 * @return 生效行数
	 */
	/*@Insert("INSERT INTO LIUZHICHAO_ORG  (ORG_ID,ORG_NAME,ORG_LOC,PID) VALUES(#{orgId},#{orgName},#{orgLoc},#{pId})")*/
	Integer insertOrg(Org org);

	/**
	 * 根据部门编号删除部门
	 * 
	 * @param orgId
	 * @return
	 */
	/*@Delete("DELETE FROM LIUZHICHAO_ORG  WHERE ORG_ID = #{orgId} ")*/
	Integer deleteOrg(String orgId);

	/**
	 * 根据部门编号删除员工
	 * 
	 * @param orgId 部门编号
	 * @return 生效行数
	 */
	/*@Delete("DELETE FROM LIUZHICHAO_USER WHERE ORG_ID = #{orgId}")*/
	Integer deleteUsers(String orgId);
	
	/**
	 * 根据部门编号修改部门信息
	 * @param orgId 部门id
	 * @return
	 */
	/*@Update("UPDATE LIUZHICHAO_ORG  SET ORG_NAME = #{orgName},ORG_LOC = #{orgLoc} WHERE ORG_ID = #{orgId}")*/
	Integer updateOrg(@Param("orgId") String orgId,@Param("orgName") String orgName,@Param("orgLoc")String orgLoc);
	
	/**
	 * 根据部门名称查询部门信息
	 * 
	 * @param orgName 部门名称
	 * @return 部门数据对象
	 */
	/*@Select("SELECT ORG_ID AS ORGID,ORG_NAME AS ORGNAME,ORG_LOC AS ORGLOC,PID AS PID "
			+ "FROM LIUZHICHAO_ORG  WHERE ORG_NAME LIKE #{orgNames} ")*/
	List<Org> getOrgByName(String orgNames);
	
	/**
	 * 根据部门编号查询部门信息
	 * @param orgId
	 * @return
	 */
	/*@Select("SELECT ORG_ID AS ORGID,ORG_NAME AS ORGNAME,ORG_LOC AS ORGLOC,PID AS PID "
			+ "FROM LIUZHICHAO_ORG  WHERE ORG_Id = #{orgId} ")*/
	Org getOrgByOrgId(String orgId);
	
	/**
	 * 查询所有部门的信息
	 * @return 部门信息集合
	 */
	/*@Select("SELECT ORG_ID AS ORGID,ORG_NAME AS ORGNAME,ORG_LOC AS ORGLOC,PID AS PID FROM LIUZHICHAO_ORG ")*/
	List<Org> getOrgs();
	
	/**
	 * 查询一共有多少条数据
	 * @return 查询条数
	 */
	/*@Select("SELECT COUNT(*) FROM LIUZHICHAO_ORG ")*/
	Integer getTotalCount();
	
	/**
	 * 根据部门名称查询一共有多少条数据
	 * @return 查询条数
	 */
	/*@Select("SELECT COUNT(*) FROM LIUZHICHAO_ORG  WHERE ORG_NAME LIKE #{orgName}")*/
	Integer getTotalCountByFuzzy(String orgName);
	
	
	/**
	 * 根据部门编号查询有多少下级部门
	 * 
	 * @param orgId
	 * @return
	 */
	/*@Select("SELECT COUNT(*) FROM LIUZHICHAO_ORG  WHERE PID = #{orgId}")*/
	Integer getCount(String orgId);

	/**
	 * 根据部门编号查询此部门员工数量
	 * 
	 * @return
	 */
	/*@Select("SELECT COUNT(*) FROM LIUZHICHAO_USER WHERE ORG_ID = #{orgId}")*/
	Integer getUsersCount(String orgId);

	/**
	 * 根据部门编号查询此部门员工信息
	 * 
	 * @param orgId
	 * @return
	 */
	/*@Select("SELECT U.ID,U.USER_ID AS USERID,U.USER_NAME AS USERNAME,U.USER_PASSWORD AS USERPASSWORD,"
			+ "U.JOB AS JOB,U.MGR_ID AS MGRID,U.ORG_ID AS ORGID,O.ORG_NAME AS ORGNAME,"
			+ "O.ORG_LOC AS ORGLOC,O.PID AS PID " + "FROM LIUZHICHAO_USER U RIGHT JOIN LIUZHICHAO_ORG  O ON U.ORG_ID = O.ORG_ID "
			+ "WHERE O.ORG_ID = #{orgId} ")*/
	List<UserAndOrg> queryUsers(String orgId);

	/**
	 * 根据部门编号查询下级部门信息
	 * 
	 * @param orgId 部门编号
	 * @return 下级部门集合信息
	 */
	/*@Select("SELECT ORG_ID AS ORGID,ORG_NAME AS ORGNAME,ORG_LOC AS ORGLOC,PID AS PID "
			+ "FROM LIUZHICHAO_ORG  WHERE PID =  #{orgId} ")*/
	List<Org> queryOrgs(String orgId);

}
