package com.xiongyayun.athena.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * AdminApplication
 *
 * @author: Yayun.Xiong
 * @date 2019-05-20
 */
@SpringBootApplication
@EnableFeignClients(basePackages = { "com.xiongyayun.athena" })
@EnableDiscoveryClient
@ComponentScan(basePackages = { "com.xiongyayun.athena" })
public class AuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}

}
