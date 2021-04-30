package com.xiongyayun.athena.security.handler;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * 认证失败事件处理器
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/4/25
 */
public abstract class AbstractAuthenticationFailureEvenHandler implements ApplicationListener<AbstractAuthenticationFailureEvent> {

	/**
	 * Handle an application event.
	 * @param event the event to respond to
	 */
	@Override
	public void onApplicationEvent(AbstractAuthenticationFailureEvent event) {
		AuthenticationException authenticationException = event.getException();
		Authentication authentication = (Authentication) event.getSource();

		handle(authenticationException, authentication);
	}

	/**
	 * 处理登录失败方法
	 * <p>
	 * @param authenticationException 登录的authentication 对象
	 * @param authentication 登录的authenticationException 对象
	 */
	public abstract void handle(AuthenticationException authenticationException, Authentication authentication);

}
