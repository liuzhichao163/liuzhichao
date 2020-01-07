package com.primeton.liuzhichao.demo.redis;

import java.lang.reflect.Method;
import java.util.Set;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MemberSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Aspect
@Component
public class RedisCacheAspect {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private JedisClientPool jedisClientPool;
	
	@Pointcut("@annotation(RedisCache)")
	public void redisCachePointcut() {
	}
	
	@Pointcut("@annotation(RedisDel)")
	public void redisDelPointcut() {
	}
	
	@Around("redisCachePointcut()")
	public Object redisCachePointcut(ProceedingJoinPoint joinPoint) throws Throwable {
		//得到类名、方法名和参数
        String redisResult = "";
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        //根据类名，方法名和参数生成key
        String key = genKey(className,methodName,args);
        logger.info("生成的key[{}]",key);
        //得到被代理的方法
        Signature signature = joinPoint.getSignature();
        if(!(signature instanceof MemberSignature)){
            throw  new IllegalArgumentException();
        }
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = joinPoint.getTarget().getClass().getMethod(methodSignature.getName(),methodSignature.getParameterTypes());
        //得到被代理的方法上的注解
        Object result = null;
        //判断方法上是否有加入缓存的注解
        if (method.isAnnotationPresent(RedisCache.class)){

            String keyName = method.getAnnotation(RedisCache.class).keyName();
            int cacheTime = method.getAnnotation(RedisCache.class).cacheTime();
            logger.info("cacheTime："+cacheTime);
            if (keyName != null && !keyName.equals("")){
                key = keyName+":"+key;
            }
            if(!jedisClientPool.exists(key)) {
                logger.info("缓存未命中");
                //缓存不存在，则调用原方法，并将结果放入缓存中
                result = joinPoint.proceed(args);
                redisResult = JSON.toJSONString(result);
                if (cacheTime==-1){
                    jedisClientPool.set(key,redisResult);
                }else {
                    jedisClientPool.set(key,redisResult,cacheTime);
                }
            } else{
                //缓存命中
                logger.info("缓存命中");
                redisResult = jedisClientPool.get(key);
                //得到被代理方法的返回值类型
                Class returnType = method.getReturnType();
                result = JSON.parseObject(redisResult,returnType);
                logger.info("redis缓存命中返回数据："+JSON.toJSONString(result));
            }
        }else {
            //执行方法。返回
            result = joinPoint.proceed(args);
        }
		return result;
	}
	
	
	@Around("redisDelPointcut()")
	public Object redisDelPointcut(ProceedingJoinPoint joinPoint) throws Throwable {
		//得到类名、方法名和参数
        String redisResult = "";
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        //根据类名，方法名和参数生成key
        String key = genKey(className,methodName,args);
        logger.info("生成的key[{}]",key);
        //得到被代理的方法
        Signature signature = joinPoint.getSignature();
        if(!(signature instanceof MemberSignature)){
            throw  new IllegalArgumentException();
        }
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = joinPoint.getTarget().getClass().getMethod(methodSignature.getName(),methodSignature.getParameterTypes());
        //得到被代理的方法上的注解

        Object result = null;

        //判断方法是否有删除缓存的注解
        if (method.isAnnotationPresent(RedisDel.class)){
            result = joinPoint.proceed(args);
            RedisDel redisDel = method.getAnnotation(RedisDel.class);
            //是否删除所有
//            boolean all = redisDel.all();
            boolean all = false;
            String keyName = redisDel.keyName();
            System.out.println("删除缓存方法s："+keyName);
            if (all){
                Set<String> keys = jedisClientPool.keys(keyName);
                for (String s1:keys){
                    jedisClientPool.del(s1);
                }
            }else {
            	if (keyName != null && !keyName.equals("")){
                    key = keyName+":"+key;
                }
            	 System.out.println("删除缓存方法key："+key);
                jedisClientPool.del(keyName);
            }
        }else {
            //执行方法。返回
            result = joinPoint.proceed(args);
        }
        return result;
	}
	
	
	/**
	 * 生成key
	 * @param className 目标类名称
	 * @param methodName  目标类的方法名
	 * @param args      目标类的参数
	 * @return  className.methodName_args[i]
	 */
	private String genKey(String className, String methodName, Object[] args) {
        StringBuilder sb = new StringBuilder("");
        sb.append(className);
        sb.append(".");
        sb.append(methodName);
        for (Object object: args) {
            logger.info("obj:"+object);
            if(object!=null) {
                sb.append("_");
                sb.append(object+"");
            }
        }
        return sb.toString();
    }
}
