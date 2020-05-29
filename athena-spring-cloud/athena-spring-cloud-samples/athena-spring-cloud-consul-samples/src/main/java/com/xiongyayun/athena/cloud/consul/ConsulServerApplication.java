package com.xiongyayun.athena.cloud.consul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * OAuth2ClientApplication
 *cloud
 * @author: Yayun.Xiong
 * @date 2019-05-20
 */
@SpringBootApplication
public class ConsulServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsulServerApplication.class, args);
	}

}
