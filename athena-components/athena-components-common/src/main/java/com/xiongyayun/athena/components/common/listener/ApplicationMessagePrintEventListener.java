package com.xiongyayun.athena.components.common.listener;

import com.xiongyayun.athena.components.common.ApplicationMessagePrinter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.springframework.util.ClassUtils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 启动完成消息输出事件
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/9/12
 */
@Order
public class ApplicationMessagePrintEventListener implements ApplicationListener<ApplicationReadyEvent> {
	private static final String BOOTSTRAP_LISTENER = "org.springframework.cloud.bootstrap.BootstrapApplicationListener";
	private static final AtomicInteger NUM = new AtomicInteger(0);

	private static boolean isCloudBootstrap(ApplicationReadyEvent event) {
		return ClassUtils.isPresent(BOOTSTRAP_LISTENER, null) && isCloudBootstrap(event.getApplicationContext().getEnvironment());
	}

	private static boolean isCloudBootstrap(Environment env) {
		return env.getProperty("spring.cloud.bootstrap.enabled", Boolean.class, true);
	}


	@Override
	public void onApplicationEvent(@NonNull ApplicationReadyEvent applicationReadyEvent) {
		if (isCloudBootstrap(applicationReadyEvent)) {
			if (NUM.get() == 1) {
				doEvent(applicationReadyEvent);
			}
			NUM.incrementAndGet();
		} else {
			doEvent(applicationReadyEvent);
		}
	}

	protected void doEvent(ApplicationReadyEvent applicationReadyEvent) {
		ApplicationMessagePrinter.main(applicationReadyEvent.getApplicationContext(), applicationReadyEvent.getSpringApplication().getMainApplicationClass());
	}

}
