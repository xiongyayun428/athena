package com.xiongyayun.athena.core.context;

import com.xiongyayun.athena.core.utils.SpringContextUtil;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class SpringContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
	@Override
	public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
		SpringContextUtil.initApplicationContextIfNotSet(configurableApplicationContext);
	}
}
