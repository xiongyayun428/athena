package com.xyy.athena.user;

import org.springframework.boot.SpringApplication;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//import org.springframework.cloud.openfeign.EnableFeignClients;
//import org.springframework.context.annotation.ComponentScan;

/**
 * UserApplication
 *
 * @author Yayun.Xiong
 * @date 2019-03-03 16:30
 */
//@SpringBootApplication
//@EnableEurekaClient
//@EnableFeignClients(basePackages = { "com.xyy.athena.role.api", "com.xyy.athena.user" })
//@ComponentScan(basePackages = { "com.xyy.athena.role", "com.xyy.athena.user" })
public class UserApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}

}
