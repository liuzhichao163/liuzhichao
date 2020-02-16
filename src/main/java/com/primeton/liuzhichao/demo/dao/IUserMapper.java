package com.primeton.liuzhichao.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.primeton.liuzhichao.demo.entity.User;
import com.primeton.liuzhichao.demo.entity.UserAndOrg;

/**
 * dao层Api
 * 
 * @author ASUS
 *
 */
@Mapper
public interface IUserMapper {
	/**
	 * 添加用户数据
	 * 
	 * @param user 用户数据对象
	 * @return 受影响的行数，如果增加成功，则返回1，否则，返回0
	 * @throws Exception
	 */
	/*@Insert("INSERT INTO LIUZHICHAO_USER" + "(USER_ID,USER_NAME," + "USER_PASSWORD," + "JOB," + "MGR_ID," + "ORG_ID) "
			+ "VALUES (#{userId},#{userName},#{userPassword},#{job},#{mgrId},#{orgId})")*/
	Integer insertUser(User user);

	/**
	 * 删除用户
	 * 
	 * @param id 用户id
	 * @return 生效行数
	 */
	/*@Delete("DELETE FROM LIUZHICHAO_USER WHERE ID = #{id}")*/
	Integer deleteUser(Integer id);
	
	/**
	 * 根据id修改用户密码
	 * 
	 * @param id          用户登陆后在session中保存的用户id
	 * @param empPassword 用户密码
	 * @param modifier    修改人
	 * @param modifyTime  修改时间
	 * @return
	 */
	/*@Update("UPDATE LIUZHICHAO_USER SET USER_PASSWORD = #{userPassword} WHERE ID = #{id}")*/
	Integer updatePassword(@Param("id") Integer id, @Param("userPassword") String userPassword);

	/**
	 * 根据id修改用户信息
	 */
	/*@Update("UPDATE LIUZHICHAO_USER SET JOB = #{job},MGR_ID = #{MgrId},ORG_ID =#{orgId} WHERE ID = #{id}")*/
	Integer updateUser(@Param("id") Integer id, @Param("job") String job, @Param("MgrId") String MgrId,
			@Param("orgId") String orgId);

	/**
	 * 查询总共多少条数据
	 * 
	 * @return
	 */
	/*@Select("SELECT COUNT(*) FROM LIUZHICHAO_USER")*/
	Integer getTotalCount();
	
	/**
	 * 查询此用户名下有多少条数据
	 * @return
	 */
	/*@Select("SELECT COUNT(*) FROM LIUZHICHAO_USER WHERE USER_NAME LIKE #{userName}")*/
	Integer getTotalCountByFuzzy(String userName);
	
	/**
	 * 根据用户名精确查询查询用户信息
	 * 
	 * @param userName 用户名
	 * @return 用户信息集合
	 */
	/*@Select("SELECT U.ID,U.USER_ID AS USERID,U.USER_NAME AS USERNAME,U.USER_PASSWORD AS USERPASSWORD,U.JOB AS JOB,"
			+ "U.MGR_ID AS MGRID,U.ORG_ID AS ORGID,O.ORG_NAME AS ORGNAME,O.ORG_LOC AS ORGLOC,O.PID AS PID "
			+ "FROM LIUZHICHAO_USER U LEFT JOIN LIUZHICHAO_ORG O ON U.ORG_ID = O.ORG_ID WHERE U.USER_NAME = #{userName} ")*/
	UserAndOrg getUserByName(String userName);
	
	/**
	 * 根据用户名模糊查询查询用户信息
	 * 
	 * @param userName 用户名
	 * @return 用户信息集合
	 */
	/*@Select("SELECT U.ID,U.USER_ID AS USERID,U.USER_NAME AS USERNAME,U.USER_PASSWORD AS USERPASSWORD,U.JOB AS JOB,"
			+ "U.MGR_ID AS MGRID,U.ORG_ID AS ORGID,O.ORG_NAME AS ORGNAME,O.ORG_LOC AS ORGLOC,O.PID AS PID "
			+ "FROM LIUZHICHAO_USER U LEFT JOIN LIUZHICHAO_ORG O ON U.ORG_ID = O.ORG_ID WHERE U.USER_NAME LIKE #{userNames} ")*/
	List<UserAndOrg> getUserByFuzzy(String userNames);

	/**
	 * 根据用户id获得用户信息(密码)
	 * 
	 * @param id 用户id
	 * @return 返回用户数据对象
	 */
	/*@Select("SELECT ID,USER_ID AS USERID,USER_NAME AS USERNAME,USER_PASSWORD AS USERPASSWORD,"
			+ "JOB AS JOB,MGR_ID AS MGRID,ORG_ID AS ORGID FROM LIUZHICHAO_USER WHERE ID = #{id}")*/
	User getUser(Integer id);

	/**
	 * 查询所有用户的有效信息
	 * 
	 * @return 含有所有用户信息的集合
	 */
	List<UserAndOrg> queryUsers();
	
	/**
	 * 根据用户名查询用户信息，带用户角色
	 * @return
	 */
	User getUserByName2(String userName);

}
