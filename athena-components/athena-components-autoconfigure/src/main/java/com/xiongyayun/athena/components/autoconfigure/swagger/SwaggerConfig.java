package com.xiongyayun.athena.components.autoconfigure.swagger;

import com.xiongyayun.athena.components.common.doc.ExtendPrintMsg;
import com.xiongyayun.athena.components.swagger.SwaggerExtendPrintMsg;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SwaggerConfig
 *
 * @author Yayun.Xiong
 * @date 2021/9/13
 */
@Configuration
public class SwaggerConfig {

	@Bean
	@ConditionalOnClass(SwaggerExtendPrintMsg.class)
	public ExtendPrintMsg swaggerExtendPrintMsg() {
		return new SwaggerExtendPrintMsg();
	}
}
