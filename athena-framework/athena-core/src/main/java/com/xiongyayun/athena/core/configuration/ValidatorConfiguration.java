package com.xiongyayun.athena.core.configuration;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * 验证机制配置
 *
 * @author: Yayun.Xiong
 * @date 2019-05-25
 */
@Configuration
public class ValidatorConfiguration {
    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
				// failFast：true  快速失败返回模式    false 普通模式
                .addProperty("hibernate.validator.fail_fast", "true")
                .buildValidatorFactory();
        return validatorFactory.getValidator();
    }

	/**
	 * 配置MethodValidationPostProcessor拦截器，以实现对方法参数的校验
	 * 在所在Controller方法上加注解@Validated生效
	 */
	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() {
		MethodValidationPostProcessor postProcessor = new MethodValidationPostProcessor();
		// 设置validator模式为快速失败返回
		postProcessor.setValidator(validator());
		return postProcessor;
	}
}
