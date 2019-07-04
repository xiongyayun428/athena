package com.xiongyayun.athena.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * 认证授权服务
 * 提供微服务间oauth2统一平台认证服务
 * 提供认证客户端、令牌、已授权管理
 *
 * @author: Yayun.Xiong
 * @date 2019-05-20
 */
@SpringBootApplication
// 开启feign RPC远程调用
@EnableFeignClients(basePackages = { "com.xiongyayun.athena" })
// 开启服务发现
@EnableDiscoveryClient
@ComponentScan(basePackages = { "com.xiongyayun.athena" })
public class AuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}

}
