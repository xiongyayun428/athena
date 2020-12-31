package com.xiongyayun.athena.sso;

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
 * SingleSignOnApplication
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2020/12/30
 */
@EnableOpenApi
@SpringBootApplication(scanBasePackages = { "com.xiongyayun.athena" })
@EnableDiscoveryClient // 开启服务注册发现功能
//@EnableFeignClients(basePackages = "com.xiongyayun.athena")
@MapperScan("com.xiongyayun.athena.sso.mapper")
@RefreshScope
public class SingleSignOnApplication {
	private static final Logger log = LoggerFactory.getLogger(SingleSignOnApplication.class);

	public static void main(String[] args) throws UnknownHostException {
		ConfigurableApplicationContext context = SpringApplication.run(SingleSignOnApplication.class, args);
		String name = ManagementFactory.getRuntimeMXBean().getName();
		String pid = name.split("@")[0];
		Environment env = context.getEnvironment();
		String port = env.getProperty("server.port", "8080"), contextPath = env.getProperty("server.servlet.context-path", "");
		String origin = "http://" + InetAddress.getLocalHost().getHostAddress() + ":" + port + contextPath;
		log.info("\n--------------------------------------------------------------------------------------------------------------------\n\t" +
						"Application '{}' is running! Pid: {}, Access URLs:\n\t" +
						"Local: \t\t\thttp://localhost:{}{}\n\t" +
						"External: \t\t" + origin + "\n\t" +
						"Druid Monitor: \t" + origin + "/druid/\n\t" +
						"Api Docs: \t\t" + origin + "/swagger-ui/\n\t" +
						"Api Docs: \t\t" + origin + "/doc.html" +
						"\n--------------------------------------------------------------------------------------------------------------------",
				env.getProperty("spring.application.name", context.getId()),
				pid,
				port,
				contextPath
		);
	}
}
