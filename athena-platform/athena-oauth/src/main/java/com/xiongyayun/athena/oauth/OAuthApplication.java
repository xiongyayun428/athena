package com.xiongyayun.athena.oauth;

import com.xiongyayun.athena.core.AthenaApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * SingleSignOnApplication
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2020/12/30
 */
@EnableOpenApi
@SpringBootApplication(scanBasePackages = { "com.xiongyayun.athena" })
@EnableDiscoveryClient // 开启服务注册发现功能
//@EnableFeignClients(basePackages = "com.xiongyayun.athena")
@RefreshScope
public class OAuthApplication {

	public static void main(String[] args) {
		AthenaApplication.run(OAuthApplication.class, args);
	}
}
