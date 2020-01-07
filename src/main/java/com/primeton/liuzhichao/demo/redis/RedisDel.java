package com.primeton.liuzhichao.demo.redis;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
@Documented
public @interface RedisDel {
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
//	Boolean all() default false;
}
