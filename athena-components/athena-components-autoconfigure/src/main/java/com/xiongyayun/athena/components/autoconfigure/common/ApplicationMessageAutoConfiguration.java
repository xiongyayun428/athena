package com.xiongyayun.athena.components.autoconfigure.common;

import com.xiongyayun.athena.components.common.ApplicationMessageService;
import com.xiongyayun.athena.components.autoconfigure.BootStartMessageService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ApplicationMessageAutoConfiguration
 *
 * @author Yayun.Xiong
 * @date 2021/9/14
 */
@Configuration
public class ApplicationMessageAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public ApplicationMessageService bootStartMessageService() {
		return new BootStartMessageService();
	}
}
