package com.xiongyayun.athena.components.common;

import com.xiongyayun.athena.components.common.doc.ExtendPrintMsg;
import com.xiongyayun.athena.components.util.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/9/13
 */
public final class ApplicationMessagePrinter {
	private static final Logger log = LoggerFactory.getLogger(ApplicationMessagePrinter.class);

	public ApplicationMessagePrinter() {
		throw new IllegalArgumentException("can not create instance!");
	}

	public static void print() {
		print(SpringContextUtil.getApplicationContext());
	}

	public static void print(ApplicationContext context) {
		print(context, null);
	}

	public static void print(Class<?> mainClass) {
		print(SpringContextUtil.getApplicationContext(), mainClass);
	}

	public static void print(ApplicationContext context, Class<?> mainClass) {
		try {
			ApplicationMessageService appMessageService = context.getBeanProvider(ApplicationMessageService.class).getIfAvailable();
			if (Objects.isNull(appMessageService)) {
				appMessageService = new ApplicationMessageService.DefaultApplicationMessageService();
			}

			ApplicationMessage appMessage = appMessageService.loadMessage(context);
			print(context, mainClass, appMessage);
		} catch (Exception e) {
			log.warn("print app start message failure, cause by: {}", e.getMessage(), e);
		}
	}

	private static void print(ApplicationContext context, Class<?> mainClass, ApplicationMessage appMessage) {
		String extendMsg = extendMsg(context, appMessage);
		if (StringUtils.hasLength(extendMsg)) {
			extendMsg = "\n\t" + extendMsg;
		}
		String msg = "\n---------------------------------------------------------------------------------------------------------------------------\n\t"
				+ "Application ''{0}'' is Running! using Java {1} on {2} with PID: {3}, Access URLs:\n\t"
				+ "Local: \t\t\t{4}://localhost:{5}{6}\n\t"
				+ "External: \t\t{7}://{8}:{9}{10}\n\t"
				+ "Profile(s): \t{11}"
				+ extendMsg
				+ "\n---------------------------------------------------------------------------------------------------------------------------"
				;
		String printMessage = MessageFormat.format(msg,
				appMessage.getApplicationName()
				, System.getProperty("java.version")
				, appMessage.getHostName()
				, appMessage.getPid()
				, appMessage.getProtocol()
				, appMessage.getPort().toString()
				, appMessage.getContextPath()
				, appMessage.getProtocol()
				, appMessage.getIp()
				, appMessage.getPort().toString()
				, appMessage.getContextPath()
				, Arrays.toString(appMessage.getProfiles())
		);
		getLogger(mainClass).info(printMessage);
	}

	private static Logger getLogger(Class<?> mainClass) {
		if (ObjectUtils.isEmpty(mainClass)) {
			StackTraceElement[] elements = Thread.getAllStackTraces().get(Thread.currentThread());
			String className = Arrays.stream(elements)
					.filter(element -> "main".equals(element.getMethodName()))
					.findFirst()
					.map(element -> element.getClassName())
					.orElse(ApplicationMessagePrinter.class.getName());
			return LoggerFactory.getLogger(className);
		}
		return LoggerFactory.getLogger(mainClass);
	}

	protected static String extendMsg(ApplicationContext context, ApplicationMessage appMessage) {
		return context.getBeansOfType(ExtendPrintMsg.class).values().stream().map(bean -> {
			List<String> extendMsg = bean.printMsg(appMessage);
			if (Objects.isNull(extendMsg)) {
				extendMsg = new ArrayList<>();
			}
			return String.join("", extendMsg);
		}).collect(Collectors.joining("\n\t"));
	}
}
