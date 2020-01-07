package com.primeton.liuzhichao.demo.redis;

import java.util.Set;

/**
 * jedis客户端的接口
 * 
 * @author ASUS
 *
 */
public interface JedisClient {
	/**
	 * 设置key，value
	 */
	String set(String key, String value);

	/**
	 * 设置key，value并设置过期时间
	 */
	String set(String key, String value, int time);

	/**
	 * 根据key得到值
	 */
	String get(String key);

	/**
	 * 根据key删除值
	 */
	Long del(String key);

	/**
	 * 根据前缀得到所有的key
	 */
	Set<String> keys(String key);

	/**
	 * 判断key是否存在
	 */
	Boolean exists(String key);

	/**
	 * 为key设置过期时间
	 */
	Long expire(String key, int seconds);

	/**
	 * 得到key的存活时间 ， key不存在-2, key永久存活-1 key存活时间（秒）
	 */
	Long ttl(String key);

	/**
	 * 将key的值，加一
	 */
	Long incr(String key);

	/**
	 * 设置map的值
	 */
	Long hset(String key, String field, String value);

	/**
	 * 得到map的值
	 */
	String hget(String key, String field);

	/**
	 * 删除map
	 */
	Long hdel(String key, String... field);

}
