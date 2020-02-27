package com.primeton.liuzhichao.demo.redis;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RedisLock {
	
	@Autowired
    private JedisClientPool jedisClientPool;
	
	//锁超时时间，防止线程进入锁后，无线执行下去造成死锁
	private int expireTime = 60 * 1000;
	

	//执行锁的方法
	public synchronized Boolean setLock(String key) {
		String lockKey = key + "_lock";
		//当前系统时间+锁过期时间 后的时间值即：锁到期时间值
		long expries = System.currentTimeMillis() + expireTime + 1;
		//将expries转换成String值，作为锁的value值
		String expriesStr = String.valueOf(expries);
		
		if(jedisClientPool.setNX(lockKey, expriesStr) == 1) {
			System.out.println("---上锁1---");
			//成功设置了分布式锁
			return true;
		}
		//设置分布式锁失败,判断上一个锁是否过期
		String currentValueStr =  jedisClientPool.get(lockKey); //获取上一个线程枷锁的时间（value就是锁到期时间）
		//如果上一个线程的value值不为空，并且vaule小于当前时间 就意味着上一个已锁过期
		if(StringUtils.isNotBlank(currentValueStr) && Long.parseLong(currentValueStr) < System.currentTimeMillis()) {
			//此时可以重新为上锁,并返回上一个锁的value值（只有一个线程能获取到设置新value并获取旧value，因为getSet()是同步方法）
			String oldValueStr = jedisClientPool.getSet(lockKey, expriesStr);
			//这里用oldValueStr和currentValueStr做对比是为了防止此处有多个线程，只有重新上锁的线程才能返回true
			if(StringUtils.isNotBlank(oldValueStr) && oldValueStr.equals(currentValueStr)) {
				System.out.println("---上锁2---");
				return true;
			}
			
		}
		System.out.println("---未上锁---");
		return false;
	}
	
	//释放锁
	public synchronized void unLock(String key) {
		String lockKey = key + "_lock";
		String currentValueStr =  jedisClientPool.get(lockKey);
		//为了让分布式锁的算法更稳键些，持有锁的客户端在解锁之前应该再检查一次自己的锁是否已经超时，再去做DEL操作，因为可能客户端因为某个耗时的操作而挂起，
        //操作完的时候锁因为超时已经被别人获得，不做检查而删除有可能会删除别人加的锁
		//超时：不删除锁； 不超时：删除锁
		if(StringUtils.isNotBlank(currentValueStr) && Long.parseLong(currentValueStr) > System.currentTimeMillis()) {
			System.out.println("------删除锁-------");
			jedisClientPool.del(lockKey);
		}
	}
	
	

}
