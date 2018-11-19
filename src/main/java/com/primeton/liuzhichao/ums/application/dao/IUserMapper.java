package com.primeton.liuzhichao.ums.application.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.primeton.liuzhichao.ums.application.entity.User;
import com.primeton.liuzhichao.ums.application.entity.UserAndOrg;

/**
 * dao层代码，负责与数据库交互用户表数据
 * 
 * @author ASUS
 *
 */
@Mapper
public interface IUserMapper {
	/**
	 * 添加用户数据
	 * @param user 用户数据对象
	 * @return 受影响的行数，如果增加成功，则返回1，否则，返回0
	 * @throws Exception
	 */
	@Insert("INSERT INTO DEMO_USER" + "(USER_ID,USER_NAME," + "USER_PASSWORD," + "JOB," + "MGR_ID," + "ORG_ID,"
			+ "CREATION_NAME," + "CREATION_TIME," + " MODIFIER," + " MODIFY_TIME) " + "VALUES"
			+ "(#{userId},#{userName}," + "#{userPassword}," + "#{job},#{mgrId}," + "#{orgId}," + "#{creationName},"
			+ "#{creationTime}," + "#{modifier}," + "#{modifyTime})")
	Integer insertUser(User user);

	/**
	 * 根据用户名查询用户
	 * @param EmpName 用户名
	 * @return 用户对象
	 */
	@Select("SELECT ID," + "USER_ID AS USERID," + "USER_NAME AS USERNAME," + "USER_PASSWORD AS USERPASSWORD,"
			+ "JOB AS JOB," + "MGR_ID AS MGRID," + "ORG_ID AS ORGID," + "CREATION_NAME AS CREATIONNAME,"
			+ "CREATION_TIME AS CREATIONTIME, " + "MODIFIER AS MODIFIER, " + "MODIFY_TIME AS MODIFYTIME " + "FROM "
			+ "DEMO_USER " + " WHERE USER_NAME = #{userName}")
	User getUserByUserName(String userName);

	/**
	 * 根据用户id获得用户信息(密码)
	 * @param id 用户id
	 * @return 返回用户数据对象
	 */
	@Select("SELECT ID," + "USER_ID AS USERID," + "USER_NAME AS USERNAME," + "USER_PASSWORD AS USERPASSWORD,"
			+ "JOB AS JOB," + "MGR_ID AS MGRID," + "ORG_ID AS ORGID," + "CREATION_NAME AS CREATIONNAME,"
			+ "CREATION_TIME AS CREATIONTIME, " + "MODIFIER AS MODIFIER, " + "MODIFY_TIME AS MODIFYTIME "
			+ "FROM DEMO_USER WHERE ID = #{id}")
	User getUserById(Integer id);

	/**
	 * 查询总共多少条数据
	 * @return
	 */
	@Select("SELECT COUNT(*) FROM DEMO_USER")
	Integer getTotalCount();

	/**
	 * 查询此用户有多少下级员工
	 * @param empId 用户id
	 * @return 查询数量
	 */
	@Select("SELECT COUNT(*) FROM DEMO_USER WHERE MGR_ID = #{userId}")
	Integer getCountByMrgId(String userId);

	/**
	 * 根据员工编号查询员工信息
	 * @param empId 员工编号
	 */
	@Select("SELECT " + "U.ID,U.USER_ID AS USERID,U.USER_NAME AS USERNAME,"
			+ "U.USER_PASSWORD AS USERPASSWORD,U.JOB AS JOB,"
			+ "U.MGR_ID AS MGRID,U.ORG_ID AS ORG_ID,U.CREATION_NAME AS CREATIONNAME,"
			+ "U.CREATION_TIME AS CREATIONTIME," + "U.MODIFIER AS MODIFIER,U.MODIFY_TIME AS MODIFYTIME,"
			+ "O.ORG_NAME AS ORGNAME,O.ORG_LOC AS ORGLOC,O.PID AS PID "
			+ "FROM DEMO_USER U LEFT JOIN DEMO_ORG O ON U.ORGID = O.ORGID " + "WHERE U.USER_ID = #{userId}")
	UserAndOrg getUserByUserId(String userId);

	/**
	 * 根据用户名查询用户信息
	 * @param userName 用户名
	 * @return 用户信息集合
	 */
	@Select("SELECT " + "U.ID,U.USER_ID AS USERID,U.USER_NAME AS USERNAME,"
			+ "U.USER_PASSWORD AS USERPASSWORD,U.JOB AS JOB,"
			+ "U.MGR_ID AS MGRID,U.ORG_ID AS ORG_ID,U.CREATION_NAME AS CREATIONNAME,"
			+ "U.CREATION_TIME AS CREATIONTIME," + "U.MODIFIER AS MODIFIER,U.MODIFY_TIME AS MODIFYTIME,"
			+ "O.ORG_NAME AS ORGNAME,O.ORG_LOC AS ORGLOC,O.PID AS PID "
			+ "FROM DEMO_USER U LEFT JOIN DEMO_ORG O ON U.ORGID = O.ORGID " + "WHERE U.USER_NAME = #{userName}")
	UserAndOrg queryUsersByUserName(String userName);

	/**
	 * 查询所有用户的有效信息
	 * @return 含有所有用户信息的集合
	 */
	@Select("SELECT U.ID,U.USER_ID AS USERID,U.USER_NAME AS USERNAME,U.USER_PASSWORD AS USERPASSWORD,"
			+ "U.JOB AS JOB,U.MGR_ID AS MGRID,U.ORG_ID AS ORGID,U.CREATION_NAME AS CREATIONNAME,"
			+ "U.CREATION_TIME AS CREATIONTIME,"
			+ "U.MODIFIER AS MODIFIER,U.MODIFY_TIME AS MODIFYTIME,O.ORG_NAME AS ORGNAME,"
			+ "O.ORG_LOC AS ORGLOC,O.PID AS PID "
			+ "FROM DEMO_USER U LEFT JOIN DEMO_ORG O ON U.ORG_ID = O.ORG_ID")
	List<UserAndOrg> queryUsers();

	/**
	 * 根据用户id修改用户密码
	 * @param id          用户登陆后在session中保存的用户id
	 * @param empPassword 用户密码
	 * @param modifier    修改人
	 * @param modifyTime  修改时间
	 * @return
	 */
	@Update("UPDATE DEMO_USER SET " + "USER_PASSWORD = #{userPassword}," + "MODIFIER = #{modifier},"
			+ "MODIFY_TIME = #{modifyTime} " + "WHERE ID = #{id}")
	Integer updatePassword(@Param("id") Integer id, @Param("userPassword") String userPassword,
			@Param("modifier") String modifier, @Param("modifyTime") Date modifyTime);

	/**
	 * 删除用户
	 * @param id 用户id
	 * @return 生效行数
	 */
	@Delete("DELETE FROM DEMO_USER WHERE ID = #{id}")
	Integer deleteUser(Integer id);

}
