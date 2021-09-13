package com.xiongyayun.athena.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import springfox.documentation.oas.annotations.EnableOpenApi;

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
@MapperScan("com.xiongyayun.athena.system.dao.*.mapper")
@RefreshScope
public class SystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SystemApplication.class, args);
	}

}
