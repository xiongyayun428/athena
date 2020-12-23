package com.xiongyayun.athena.oauth2.sso;

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
public class OAuth2SsoApplication {

	public static void main(String[] args) {
		SpringApplication.run(OAuth2SsoApplication.class, args);
	}

}
