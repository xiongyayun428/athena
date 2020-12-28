package com.xiongyayun.athena.system;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import springfox.documentation.oas.annotations.EnableOpenApi;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * UserApplication
 *
 * @author Yayun.Xiong
 * @date 2019-03-03 16:30
 */
//@EnableFeignClients(basePackages = { "com.xiongyayun.athena.role.api", "com.xiongyayun.athena.user" })

@EnableOpenApi
@SpringBootApplication(scanBasePackages = { "com.xiongyayun.athena" })
@EnableDiscoveryClient // 开启服务注册发现功能
//@EnableFeignClients(basePackages = "com.xiongyayun.athena")
@MapperScan("com.xiongyayun.athena.system.mapper")
@RefreshScope
public class SystemApplication {
	private static final Logger log = LoggerFactory.getLogger(SystemApplication.class);

	public static void main(String[] args) throws UnknownHostException {
		ConfigurableApplicationContext context = SpringApplication.run(SystemApplication.class, args);
		String name = ManagementFactory.getRuntimeMXBean().getName();
		String pid = name.split("@")[0];
		Environment env = context.getEnvironment();
		String port = env.getProperty("server.port", "8080"), contextPath = env.getProperty("server.servlet.context-path", "");
		log.info("\n--------------------------------------------------------------------------------------------------------------------\n\t" +
						"Application '{}' is running! Pid: {}, Access URLs:\n\t" +
						"Local: \t\thttp://localhost:{}{}\n\t" +
						"External: \thttp://{}:{}{}" +
						"\n--------------------------------------------------------------------------------------------------------------------",
				env.getProperty("spring.application.name", context.getId()),
				pid,
				port,
				contextPath,
				InetAddress.getLocalHost().getHostAddress(),
				port,
				contextPath
		);
	}

}
