package com.primeton.liuzhichao.demo.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Lock {
	
	@Autowired
    private JedisClientPool jedisClientPool;
	
	//锁的Key值
    String lockKey;
     
	
	public void setLockKey(String lockKey) {
		this.lockKey = lockKey;
	}


	public Boolean setLock() {
		jedisClientPool.set("111", "333");
		return true;
	}
}
