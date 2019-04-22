package com.xyy.athena.access.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class Swagger2Config implements WebMvcConfigurer {
	
	/**
     * 添加摘要信息(Docket)
     */
    @Bean
    public Docket controllerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
	        .apiInfo(apiInfo())
	        .select()
	        .apis(RequestHandlerSelectors.basePackage("com.xyy.athena"))
	        .paths(PathSelectors.any())
	        .build();
    }
    
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
    		.title("雅典娜_接口文档")
            .description("雅典娜_接口描述文档")
            .contact(new Contact("Yayun.Xiong", "http://www.xiongyayun.com", "xiongyayun428@163.com"))
            .version("1.0")
            .build();
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
