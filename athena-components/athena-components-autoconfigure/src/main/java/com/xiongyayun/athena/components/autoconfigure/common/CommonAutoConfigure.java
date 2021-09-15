package com.xiongyayun.athena.components.autoconfigure.common;

import com.xiongyayun.athena.components.common.filter.AthenaCorsFilter;
import com.xiongyayun.athena.components.common.filter.RequestNoFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * CommonAutoConfigure
 *
 * @author Yayun.Xiong
 * @date 2021/9/14
 */
@Configuration
public class CommonAutoConfigure {

	@Value("app.cors.allow-origin")
	private String allowOrigin;

	@Bean
	public AthenaCorsFilter athenaCorsFilter() {
		return new AthenaCorsFilter(allowOrigin);
	}

	@Bean
	public RequestNoFilter requestNoFilter() {
		return new RequestNoFilter();
	}

//	@Bean
//	public Validator validator() {
//		ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
//				.configure()
//				// failFast：true  快速失败返回模式    false 普通模式
//				.addProperty("hibernate.validator.fail_fast", "true")
//				.buildValidatorFactory();
//		return validatorFactory.getValidator();
//	}

//	/**
//	 * 配置MethodValidationPostProcessor拦截器，以实现对方法参数的校验
//	 * 在所在Controller方法上加注解@Validated生效
//	 */
//	@Bean
//	public MethodValidationPostProcessor methodValidationPostProcessor() {
//		MethodValidationPostProcessor postProcessor = new MethodValidationPostProcessor();
//		// 设置validator模式为快速失败返回
//		postProcessor.setValidator(validator());
//		return postProcessor;
//	}


}
