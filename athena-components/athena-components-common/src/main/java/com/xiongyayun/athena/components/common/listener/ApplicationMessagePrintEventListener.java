package com.xiongyayun.athena.core.listener;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.core.annotation.Order;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 启动完成消息输出事件
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/9/12
 */
@Order
public class ApplicationMessagePrintEventListener extends SpringApplicationEventListener<ApplicationReadyEvent> {
	private static AtomicInteger num = new AtomicInteger(0);

	@Override
	protected void doEvent(ApplicationReadyEvent applicationReadyEvent) {
		ApplicationMessagePrinter.print(applicationReadyEvent.getApplicationContext(), applicationReadyEvent.getSpringApplication().getMainApplicationClass());
	}

	@Override
	protected AtomicInteger getNum() {
		return ApplicationMessagePrintEventListener.num;
	}
}
