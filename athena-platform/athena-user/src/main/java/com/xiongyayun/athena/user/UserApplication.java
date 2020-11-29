package com.xiongyayun.athena.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//import org.springframework.cloud.openfeign.EnableFeignClients;
//import org.springframework.context.annotation.ComponentScan;

/**
 * UserApplication
 *
 * @author: Yayun.Xiong
 * @date 2019-03-03 16:30
 */
@SpringBootApplication(scanBasePackages = { "com.xiongyayun.athena.user" })
//@EnableEurekaClient
//@EnableFeignClients(basePackages = { "com.xiongyayun.athena.role.api", "com.xiongyayun.athena.user" })
public class UserApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}

}
