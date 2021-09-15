package com.xiongyayun.athena.components.common;

import org.springframework.context.ApplicationContext;

/**
 * AppMessageService
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/09/12
 */
public interface ApplicationMessageService {
	ApplicationMessage loadMessage(ApplicationContext context);

	class DefaultApplicationMessageService implements ApplicationMessageService {

		@Override
		public ApplicationMessage loadMessage(ApplicationContext context) {
			return new ApplicationMessage();
		}
	}
}
