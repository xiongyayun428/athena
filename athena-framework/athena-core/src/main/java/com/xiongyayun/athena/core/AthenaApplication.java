package com.xiongyayun.athena.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ClassUtils;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * AthenaApplication
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/4/25
 */
public class AthenaApplication extends SpringApplication {
//	final static Logger log = LoggerFactory.getLogger(AthenaApplication.class);
	Logger log;
	Class<?> primarySources;

	public AthenaApplication(Class<?>... primarySources) {
		this(null, primarySources);
	}

	public AthenaApplication(ResourceLoader resourceLoader, Class<?>... primarySources) {
		super(resourceLoader, primarySources);
		this.primarySources = Arrays.stream(primarySources).findFirst().get();
		log = LoggerFactory.getLogger(this.primarySources);
	}

	@Override
	public ConfigurableApplicationContext run(String... args) {
		ConfigurableApplicationContext context = super.run(args);
		String applicationName = (this.primarySources != null) ? ClassUtils.getShortName(this.primarySources) : "Application";
		String jvmName = ManagementFactory.getRuntimeMXBean().getName();
		String pid = jvmName.split("@")[0];
		String hostname = jvmName.split("@")[1];
		Environment env = context.getEnvironment();
		String port = env.getProperty("server.port", "8080"), contextPath = env.getProperty("server.servlet.context-path", "/");
		try {

			String origin = "http://" + InetAddress.getLocalHost().getHostAddress() + ":" + port + (contextPath.endsWith("/") ? contextPath : contextPath + "/");
			log.info("\n---------------------------------------------------------------------------------------------------------------------------\n\t" +
							applicationName + " '{}' is Running! using Java " + System.getProperty("java.version") + " on " + InetAddress.getLocalHost().getHostName() + " with PID: {}, Access URLs:\n\t" +
							"Local: \t\t\thttp://localhost:{}{}\n\t" +
							"External: \t\t" + origin + "\n\t" +
							"Api Docs: \t\t" + origin + "swagger-ui/\n\t" +
							"Api Docs: \t\t" + origin + "doc.html\n\t" +
							"Druid Monitor: \t" + origin + "druid/" +
							"\n---------------------------------------------------------------------------------------------------------------------------",
					env.getProperty("spring.application.name", context.getId()),
					pid,
					port,
					contextPath
			);
		} catch (UnknownHostException e) {
			log.error(e.getMessage(), e);
		}
		return context;
	}

	public static ConfigurableApplicationContext run(Class<?> primarySource, String... args) {
		return run(new Class<?>[] { primarySource }, args);
	}

	public static ConfigurableApplicationContext run(Class<?>[] primarySources, String[] args) {
		return new AthenaApplication(primarySources).run(args);
	}
}
