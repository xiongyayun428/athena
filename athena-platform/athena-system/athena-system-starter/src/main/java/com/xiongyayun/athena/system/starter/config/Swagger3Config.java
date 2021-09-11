package com.xiongyayun.athena.system.starter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.*;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.*;

/**
 * Swagger3Config
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2020/12/29
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
	@Value("${spring.application.description:项目描述信息}")
	private String applicationDescription;

	@Value("${version:1.0.0}")
	private String version;

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
//				.apis(RequestHandlerSelectors.withMethodAnnotation(Operation.class))
				.apis(RequestHandlerSelectors.any())
				// 过滤什么路径
				.paths(PathSelectors.any())
				.build()
//				.globalRequestParameters(getGlobalRequestParameters())
				.globalResponses(HttpMethod.GET, getGlobalResponseMessage())
				.globalResponses(HttpMethod.POST, getGlobalResponseMessage())
				.globalResponses(HttpMethod.PUT, getGlobalResponseMessage())
				.globalResponses(HttpMethod.DELETE, getGlobalResponseMessage())
				// 支持的通讯协议集合
				.protocols(this.newHashSet("https", "http"))
//				// 授权信息设置，必要的header token等认证信息
//				.securitySchemes(securitySchemes())
//				// 授权信息全局应用
//				.securityContexts(securityContexts())
				;
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title(applicationName + " Api Docs")
				.description(applicationDescription)
				.contact(new Contact("熊亚运", "http://www.xiongyayun.com", "xiongyayun428@163.com"))
				.version(version)
				.build();
	}

//	/**
//	 * 设置授权信息
//	 */
//	private List<SecurityScheme> securitySchemes() {
//		return Collections.singletonList(new ApiKey("BASE_TOKEN", "token", "pass"));
//	}
//
//	/**
//	 * 授权信息全局应用
//	 */
//	private List<SecurityContext> securityContexts() {
//		return Collections.singletonList(SecurityContext.builder().securityReferences(Collections.singletonList(new SecurityReference("BASE_TOKEN", new AuthorizationScope[] {new AuthorizationScope("global", "")}))).build());
//	}

	/**
	 * 生成全局通用参数
	 * @return
	 */
	private List<RequestParameter> getGlobalRequestParameters() {
		List<RequestParameter> parameters = new ArrayList<>();
		parameters.add(new RequestParameterBuilder()
				.name("appid")
				.description("平台id")
				.required(true)
				.in(ParameterType.QUERY)
				.query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
				.required(false)
				.build());
		parameters.add(new RequestParameterBuilder()
				.name("udid")
				.description("设备的唯一id")
				.required(true)
				.in(ParameterType.QUERY)
				.query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
				.required(false)
				.build());
		parameters.add(new RequestParameterBuilder()
				.name("version")
				.description("客户端的版本号")
				.required(true)
				.in(ParameterType.QUERY)
				.query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
				.required(false)
				.build());
		return parameters;
	}

	/**
	 * 生成通用响应信息
	 * @return
	 */
	private List<Response> getGlobalResponseMessage() {
		List<Response> responseList = new ArrayList<>();
		responseList.add(new ResponseBuilder().code("200").description("请求成功").build());
		responseList.add(new ResponseBuilder().code("400").description("参数错误").build());
		responseList.add(new ResponseBuilder().code("401").description("没有认证").build());
		responseList.add(new ResponseBuilder().code("403").description("没有访问权限").build());
		responseList.add(new ResponseBuilder().code("404").description("找不到资源").build());
		responseList.add(new ResponseBuilder().code("500").description("服务器内部错误").build());
		responseList.add(new ResponseBuilder().code("503").description("Hystrix异常").build());
		return responseList;
	}

	@SafeVarargs
	private final <T> Set<T> newHashSet(T... ts) {
		if (ts.length > 0) {
			return new LinkedHashSet<>(Arrays.asList(ts));
		}
		return null;
	}
}
