package com.xyy.athena.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

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
@tk.mybatis.spring.annotation.MapperScan("com.xyy.athena.*.mapper")
@EnableTransactionManagement
public class AdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
	}

}
