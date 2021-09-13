package com.xiongyayun.athena.core.listener;

import com.xiongyayun.athena.core.utils.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class ApplicationMessagePrinter {
	private static final Logger log = LoggerFactory.getLogger(ApplicationMessagePrinter.class);

	public ApplicationMessagePrinter() {
		throw new IllegalArgumentException("can not create instance!");
	}

	public static void printAppMessage() {
		printAppMessage(SpringContextUtil.getApplicationContext(), null);
	}

	public static void printAppMessage(ApplicationContext context) {
		printAppMessage(context, null);
	}

	public static void printAppMessage(Class<?> mainClass) {
		printAppMessage(SpringContextUtil.getApplicationContext(), mainClass);
	}

	public static void printAppMessage(ApplicationContext context, Class<?> mainClass) {
		try {
			AppMessageService appMessageService = context.getBeanProvider(AppMessageService.class).getIfAvailable();
			if (Objects.isNull(appMessageService)) {
				appMessageService = new AppMessageService.DefaultAppMessageService();
			}
			AppMessage appMessage = appMessageService.loadAppMessage(context);
			printAppMessage(mainClass, appMessage);
		} catch (Exception e) {
			log.warn("print app start message failure, cause by: {}", e.getMessage());
		}
	}

	private static void printAppMessage(Class<?> mainClass, AppMessage appMessage) {
//		String applicationName = (this.primarySources != null) ? ClassUtils.getShortName(this.primarySources) : "Application";
//		String jvmName = ManagementFactory.getRuntimeMXBean().getName();
//		String pid = jvmName.split("@")[0];
//		String hostname = jvmName.split("@")[1];
//		Environment env = context.getEnvironment();
//		String port = env.getProperty("server.port", "8080"), contextPath = env.getProperty("server.servlet.context-path", "/");
//		try {
//
//			String origin = "http://" + InetAddress.getLocalHost().getHostAddress() + ":" + port + (contextPath.endsWith("/") ? contextPath : contextPath + "/");
//			log.info("\n---------------------------------------------------------------------------------------------------------------------------\n\t" +
//							applicationName + " '{}' is Running! using Java " + System.getProperty("java.version") + " on " + InetAddress.getLocalHost().getHostName() + " with PID: {}, Access URLs:\n\t" +
//							"Local: \t\t\thttp://localhost:{}{}\n\t" +
//							"External: \t\t" + origin + "\n\t" +
//							"Profile(s): \t\t" + origin + "\n\t" +
//							"Api Docs: \t\t" + origin + "swagger-ui/\n\t" +
//							"Api Docs: \t\t" + origin + "doc.html\n\t" +
//							"Druid Monitor: \t" + origin + "druid/" +
//							"\n---------------------------------------------------------------------------------------------------------------------------",
//					env.getProperty("spring.application.name", context.getId()),
//					pid,
//					port,
//					contextPath
//			);
//		} catch (UnknownHostException e) {
//			log.error(e.getMessage(), e);
//		}
		String msg = "\n---------------------------------------------------------------------------------------------------------------------------\n\t"
				+ "Application ''{0}'' is Running! using Java {1} on {2} with PID: {3}, Access URLs:\n\t"
				+ "Local: \t\t\t{4}://localhost:{5}{6}\n\t"
				+ "External: \t\t{7}://{8}:{9}{10}\n\t"
				+ "Profile(s): \t\t{11}"
				+ Arrays.toString(extendMsg(appMessage))
				+ "\n---------------------------------------------------------------------------------------------------------------------------"
				;
		String printMessage = MessageFormat.format(msg,
				appMessage.getApplicationName()
				, System.getProperty("java.version")
				, appMessage.getHostName()
				, appMessage.getPid()
				, appMessage.getProtocol()
				, appMessage.getPort()
				, appMessage.getContextPath()
				, appMessage.getProtocol()
				, appMessage.getIp()
				, appMessage.getPort()
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

	protected static String[] extendMsg(AppMessage appMessage) {
		List<String> msg = new ArrayList<>();
		msg.add("\n\tApi Docs: \t\t" + appMessage.getProtocol() + "://" + appMessage.getIp() + ":" + appMessage.getPort() + appMessage.getContextPath() + "swagger-ui/\n\t");
		msg.add("Api Docs: \t\t" + appMessage.getProtocol() + "://" + appMessage.getIp() + ":" + appMessage.getPort() + appMessage.getContextPath() + "doc.html\n\t");
		msg.add("Druid Monitor: \t" + appMessage.getProtocol() + "://" + appMessage.getIp() + ":" + appMessage.getPort() + appMessage.getContextPath() + "druid/");
		return (String[]) msg.toArray();
	}
}
