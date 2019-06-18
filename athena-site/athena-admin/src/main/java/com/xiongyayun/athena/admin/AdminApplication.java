package com.xiongyayun.athena.admin;

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
//@EnableFeignClients(basePackages = { "com.xiongyayun.athena.role.api", "com.xiongyayun.athena.user" })
@ComponentScan(basePackages = { "com.xiongyayun.athena" })
@tk.mybatis.spring.annotation.MapperScan("com.xiongyayun.athena.*.mapper")
@EnableTransactionManagement
public class AdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
	}

}
