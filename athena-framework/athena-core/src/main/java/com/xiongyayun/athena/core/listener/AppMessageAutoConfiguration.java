package com.xiongyayun.athena.core.listener;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppMessageAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public AppMessageService bootStartMessageService() {
		return new BootStartMessageService();
	}
}
