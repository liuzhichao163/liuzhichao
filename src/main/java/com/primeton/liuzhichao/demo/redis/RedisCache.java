package com.primeton.liuzhichao.demo.redis;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;

/**
 * 自定义注解 
 * 如果报错：RetentionPolicy cannot be resolved to a variable
 * 则手动导入下面的注解即可
 * import java.lang.annotation.RetentionPolicy; import
 * java.lang.annotation.ElementType;
 * 
 * @author ASUS
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
@Documented
public @interface RedisCache {
	/**
	 * @Description:缓存的前缀
	 * @return
	 */
	String keyName() default "";
	
	/**
	 * @Description: 数据缓存时间单位s秒
	 * @Param:  默认10分钟
	 * @return
	 */
	int cacheTime() default 6;

}
