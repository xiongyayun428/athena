package com.xiongyayun.athena.cloud.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * EurekaServerApplication
 *
 * @author: Yayun.Xiong
 * @date 2020-05-27
 */
@SpringBootApplication
//@EnableZuulProxy
//@EnableDiscoveryClient
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

}
