package com.primeton.liuzhichao.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 项目启动类
 * 
 * @author ASUS
 *
 */

@EnableTransactionManagement // 事物注解
@SpringBootApplication
@EnableSwagger2
public class DemoApplication {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(DemoApplication.class, args);

	}

}
