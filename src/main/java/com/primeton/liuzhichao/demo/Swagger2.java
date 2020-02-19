package com.primeton.liuzhichao.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 文档注解
 * 
 * @author ASUS
 *
 */
@Configuration
@EnableSwagger2
public class Swagger2 extends WebMvcConfigurationSupport {
	
	
	@Value("${images.mapping}")  //图片访问的url路径
	private String mappingUrl;
	
	@Value("${images.upload}")
	private String uploadUrl;  //图片上传的保存路径
	
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				// 自行修改为自己的包路径
				.apis(RequestHandlerSelectors.basePackage("com.primeton.liuzhichao.demo.controller"))
				.paths(PathSelectors.any()).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("swagger-api文档").description("swagger接入教程")
				// 服务条款网址
				.termsOfServiceUrl("https://blog.csdn.net/ysk_xh_521").version("1.0").build();
	}

	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/META-INF/resources/").setCachePeriod(0);
		//图片虚拟地址映射
		registry.addResourceHandler(mappingUrl+"/**").addResourceLocations("file:"+uploadUrl);
	}

}
