package com.xiongyayun.athena.spring.boot.oauth2.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * OAuth2ClientApplication
 *
 * @author Yayun.Xiong
 * @date 2019-05-20
 */
@SpringBootApplication
@ComponentScan(basePackages = { "com.xiongyayun.athena" })
public class OAuth2ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OAuth2ServerApplication.class, args);
	}

}
