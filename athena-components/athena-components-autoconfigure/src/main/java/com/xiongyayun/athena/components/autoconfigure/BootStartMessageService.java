package com.xiongyayun.athena.components.autoconfigure;

import com.xiongyayun.athena.components.common.ApplicationMessage;
import com.xiongyayun.athena.components.common.ApplicationMessageService;
import com.xiongyayun.athena.components.util.NetUtil;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import java.lang.management.ManagementFactory;
import java.util.Objects;

/**
 * BootStartMessageService
 *
 * @author Yayun.Xiong
 * @date 2021/9/14
 */
public class BootStartMessageService implements ApplicationMessageService {
	@Override
	public ApplicationMessage loadMessage(ApplicationContext context) {
		ApplicationMessage appMessage = new ApplicationMessage();

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
