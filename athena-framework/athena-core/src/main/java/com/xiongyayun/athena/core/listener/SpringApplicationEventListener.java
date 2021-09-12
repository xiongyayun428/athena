package com.xiongyayun.athena.core.listener;

import org.springframework.boot.context.event.*;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.util.ClassUtils;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class SpringApplicationEventListener<E extends SpringApplicationEvent> implements ApplicationListener<E> {
	private static final String BOOTSTRAP_LISTENER = "org.springframework.cloud.bootstrap.BootstrapApplicationListener";
	private AtomicInteger num = new AtomicInteger(0);

	protected static boolean isBootstrap(SpringApplicationEvent event) {
		return ClassUtils.isPresent(BOOTSTRAP_LISTENER, null) && isCloudBootstrap(event);
	}

	private static boolean isCloudBootstrap(SpringApplicationEvent event) {
		if (event instanceof ApplicationContextInitializedEvent) {
			return isCloudBootstrap(((ApplicationContextInitializedEvent) event).getApplicationContext().getEnvironment());
		} else if (event instanceof ApplicationEnvironmentPreparedEvent) {
			return isCloudBootstrap(((ApplicationEnvironmentPreparedEvent) event).getEnvironment());
		} else if (event instanceof ApplicationFailedEvent) {
			return isCloudBootstrap(((ApplicationFailedEvent) event).getApplicationContext().getEnvironment());
		} else if (event instanceof ApplicationPreparedEvent) {
			return isCloudBootstrap(((ApplicationPreparedEvent) event).getApplicationContext().getEnvironment());
		} else if (event instanceof ApplicationReadyEvent) {
			return isCloudBootstrap(((ApplicationReadyEvent) event).getApplicationContext().getEnvironment());
		} else if (event instanceof ApplicationStartedEvent) {
			return isCloudBootstrap(((ApplicationStartedEvent) event).getApplicationContext().getEnvironment());
		} else if (event instanceof ApplicationStartingEvent) {
			return false;
		} else {
			return false;
		}
	}

	private static boolean isCloudBootstrap(Environment env) {
		return env.getProperty("spring.cloud.bootstrap.enabled", Boolean.class, true);
	}

	@Override
	public void onApplicationEvent(E e) {
		if (isBootstrap(e) && getNum().get() == 0) {
			getNum().incrementAndGet();
			return;
		}

		if (getNum().get() == 1) {
			doEvent(e);
			getNum().incrementAndGet();
		}
	}

	/**
	 * 执行事件监听逻辑
	 * @param e
	 */
	protected abstract void doEvent(E e);

	protected AtomicInteger getNum() {
		return this.num;
	}
}
