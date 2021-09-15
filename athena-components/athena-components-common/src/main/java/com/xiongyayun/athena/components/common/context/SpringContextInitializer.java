package com.xiongyayun.athena.components.common.context;

import com.xiongyayun.athena.components.util.SpringContextUtil;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * SpringContextInitializer
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/9/14
 */
public class SpringContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
	@Override
	public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
		SpringContextUtil.initApplicationContextIfNotSet(configurableApplicationContext);
	}
}
