package com.xiongyayun.athena.system;

import com.xiongyayun.athena.core.AthenaApplication;
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
@MapperScan("com.xiongyayun.**.mapper")
@RefreshScope
public class SystemApplication {

	public static void main(String[] args) {
		AthenaApplication.run(SystemApplication.class, args);
	}

}
