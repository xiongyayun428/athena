package com.xyy.athena.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * AdminApplication
 *
 * @author Yayun.Xiong
 * @date 2019-05-20
 */
@SpringBootApplication
//@EnableEurekaClient
//@EnableFeignClients(basePackages = { "com.xyy.athena.role.api", "com.xyy.athena.user" })
@ComponentScan(basePackages = { "com.xyy.athena" })
public class AdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
	}

}
