package com.xiongyayun.athena.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//import org.springframework.cloud.openfeign.EnableFeignClients;
//import org.springframework.context.annotation.ComponentScan;

/**
 * UserApplication
 *
 * @author Yayun.Xiong
 * @date 2019-03-03 16:30
 */
@SpringBootApplication(scanBasePackages = { "com.xiongyayun.athena" })
@MapperScan("com.xiongyayun.athena.system.mapper")
//@EnableEurekaClient
//@EnableFeignClients(basePackages = { "com.xiongyayun.athena.role.api", "com.xiongyayun.athena.user" })
public class SystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SystemApplication.class, args);
	}

}
