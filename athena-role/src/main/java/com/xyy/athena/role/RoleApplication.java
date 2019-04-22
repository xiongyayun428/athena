package com.xyy.athena.role;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 角色服务
 *
 * @author Yayun.Xiong
 * @date 2019-04-22
 */
@SpringBootApplication
@EnableEurekaClient
public class RoleApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoleApplication.class, args);
	}

}
