package com.primeton.liuzhichao.ums.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;


import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * 项目启动类
 * @author ASUS
 *
 */

@EnableTransactionManagement    // 事物注解
@SpringBootApplication
@EnableSwagger2
public class UserApplication {
	public static void main(String[] args) throws Exception {
	 ConfigurableApplicationContext context = SpringApplication.run(UserApplication.class, args);
	
	}

}
