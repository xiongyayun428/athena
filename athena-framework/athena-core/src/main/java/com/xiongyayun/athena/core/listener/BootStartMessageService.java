package com.xiongyayun.athena.core.listener;

import com.xiongyayun.athena.core.utils.NetUtil;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import java.lang.management.ManagementFactory;
import java.util.Objects;

public class BootStartMessageService implements AppMessageService {
	@Override
	public AppMessage loadAppMessage(ApplicationContext context) {
		AppMessage appMessage = new AppMessage();

		String jvmName = ManagementFactory.getRuntimeMXBean().getName();
		appMessage.setPid(jvmName.split("@")[0]);
		appMessage.setHostName(jvmName.split("@")[1]);

		Environment env = context.getEnvironment();
		appMessage.setApplicationName(env.getProperty("spring.application.name", context.getId()));

		ServerProperties server = context.getBean(ServerProperties.class);
		if (server.getSsl() != null && server.getSsl().getKeyStore() != null) {
			appMessage.setProtocol("https");
		} else {
			appMessage.setProtocol("http");
		}
		appMessage.setIp(getActualIp());
		appMessage.setPort(Objects.isNull(server.getPort()) ? 8080 : server.getPort());
		String[] profiles = env.getActiveProfiles();
		appMessage.setProfiles(profiles);
		if (server.getServlet() != null && server.getServlet().getContextPath() != null) {
			String contextPath = server.getServlet().getContextPath();
			appMessage.setContextPath(contextPath.endsWith("/") ? contextPath : contextPath + "/");
		} else {
			appMessage.setContextPath("/");
		}


		return appMessage;
	}

	protected String getActualIp() {
		return NetUtil.getLocalIpAddress();
	}
}
