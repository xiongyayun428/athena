package com.xiongyayun.athena.components.common.listener;

import com.xiongyayun.athena.components.common.ApplicationMessagePrinter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;

/**
 * 启动完成消息输出事件
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/9/12
 */
@Order
public class ApplicationMessagePrintEventListener implements ApplicationListener<ApplicationReadyEvent> {

	@Override
	public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
		ApplicationMessagePrinter.print(applicationReadyEvent.getApplicationContext(), applicationReadyEvent.getSpringApplication().getMainApplicationClass());
	}

}
