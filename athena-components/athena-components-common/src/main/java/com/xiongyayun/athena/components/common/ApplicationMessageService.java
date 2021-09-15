package com.xiongyayun.athena.components.common;

import org.springframework.context.ApplicationContext;

/**
 * AppMessageService
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/09/12
 */
public interface AppMessageService {
	AppMessage loadAppMessage(ApplicationContext context);

//	default AppMessage loadAppMessage(ApplicationContext context) {
//		return new AppMessage();
//	}

	class DefaultAppMessageService implements AppMessageService {

		@Override
		public AppMessage loadAppMessage(ApplicationContext context) {
			return new AppMessage();
		}
	}
}
