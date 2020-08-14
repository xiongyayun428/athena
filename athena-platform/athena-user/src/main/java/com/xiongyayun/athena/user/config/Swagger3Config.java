package com.xiongyayun.athena.user.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Swagger3Config
 *
 * @author: <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date: 2020/8/10
 */
@EnableOpenApi
@Configuration
public class Swagger3Config implements WebMvcConfigurer {
	/**
	 * 项目应用名
	 */
	@Value("${spring.application.name}")
	private String applicationName;
	/**
	 * 项目描述信息
	 */
	@Value("${spring.application.description}")
	private String applicationDescription;

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.OAS_30).pathMapping("/")
				.enable(true)
				.apiInfo(this.apiInfo())
				// 指定需要发布到Swagger的接口目录，不支持通配符
				.select()
				/**
				 * RequestHandlerSelectors配置扫描接口的方式
				 * basePackage:基本扫描包
				 * any:是扫描全部
				 * none：不扫描
				 * withMethodAnnotation：扫描方法上的注解
				 * withClassAnnotation：扫描类上的注解，参数是一个注解的反射
				 */
//				.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
				.apis(RequestHandlerSelectors.any())
				// 过滤什么路径
				.paths(PathSelectors.any())
				.build()
				// 支持的通讯协议集合
				.protocols(this.newHashSet("https", "http"));
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title(applicationName + " Api Doc")
				.description(applicationDescription)
				.contact(new Contact("xiongyayun", "http://www.xiongyayun.com", "xiongyayun428@163.com"))
				.version("1.0.0")
				.build();
	}

	@SafeVarargs
	private final <T> Set<T> newHashSet(T... ts) {
		if (ts.length > 0) {
			return new LinkedHashSet<>(Arrays.asList(ts));
		}
		return null;
	}
}
