package com.primeton.liuzhichao.demo.redis;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

//@Service
@Component
public class JedisClientPool implements JedisClient{
	
	@Autowired  //jedis连接池
	private JedisPool jedisPool;
	
	@Override
	public String set(String key, String value) {
		//从连接池中获取一个连接
		Jedis jedis = jedisPool.getResource();
        String result = jedis.set(key, value);
        System.out.println("-----set方法-------");
        jedis.close(); 
        return result;
	}

	@Override
	public String set(String key, String value, int time) {
		Jedis jedis = jedisPool.getResource();
        String set = jedis.set(key, value);
        Long expire = jedis.expire(key, time);
        return set;
	}

	@Override
	public String get(String key) {
		Jedis jedis = jedisPool.getResource();
        String result = jedis.get(key);
        jedis.close();
        System.out.println("get方法："+JSON.toJSONString(result));
        return result;
	}

	@Override
	public Long del(String key) {
		Jedis jedis = jedisPool.getResource();
        Long del = jedis.del(key);
        jedis.close();
        return del;
	}

	@Override
	public Set<String> keys(String key) {
		Jedis jedis = jedisPool.getResource();
        Set<String> keys = jedis.keys(key);
        return keys;
	}

	@Override
	public Boolean exists(String key) {
		Jedis jedis = jedisPool.getResource();
        Boolean result = jedis.exists(key);
        jedis.close();
        return result;
	}

	@Override
	public Long expire(String key, int seconds) {
		Jedis jedis = jedisPool.getResource();
        Long result = jedis.expire(key, seconds);
        jedis.close();
        return result;
	}

	@Override
	public Long ttl(String key) {
		Jedis jedis = jedisPool.getResource();
        Long result = jedis.ttl(key);
        jedis.close();
        return result;
	}

	@Override
	public Long incr(String key) {
		 Jedis jedis = jedisPool.getResource();
	        Long result = jedis.incr(key);
	        jedis.close();
	        return result;
	}

	@Override
	public Long hset(String key, String field, String value) {
		 Jedis jedis = jedisPool.getResource();
	        Long result = jedis.hset(key, field, value);
	        jedis.close();
	        return result;
	}

	@Override
	public String hget(String key, String field) {
		 Jedis jedis = jedisPool.getResource();
	     String result = jedis.hget(key, field);
	     jedis.close();
	     return result;
	}

	@Override
	public Long hdel(String key, String... field) {
		Jedis jedis = jedisPool.getResource();
        Long result = jedis.hdel(key, field);
        jedis.close();
        return result;
	}

	@Override
	public Long setNX(String key, String value) {
		Jedis jedis = jedisPool.getResource();
		System.out.println("===setNX==="+key+"---"+value);
		Long result = jedis.setnx(key, value);
		System.out.println("===result==="+result);
		jedis.close();
		return result;
	}

	@Override
	public String getSet(String key, String value) {
		Jedis jedis = jedisPool.getResource();
		String result = jedis.getSet(key, value);
		jedis.close();
		return result;
	}

}
