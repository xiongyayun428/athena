package com.xiongyayun.athena.components.autoconfigure.web.embedded;

import io.undertow.Undertow;
import io.undertow.UndertowOptions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * EmbeddedWebServerAutoConfiguration
 *
 * @author Yayun.Xiong
 * @date 2021/9/14
 */
@Configuration()
@ConditionalOnWebApplication
@EnableConfigurationProperties({ServerProperties.class})
public class EmbeddedWebServerAutoConfiguration {

	@Bean
	@ConditionalOnClass(Undertow.class)
	public UndertowServletWebServerFactory undertowServletWebServerFactory() {
		UndertowServletWebServerFactory factory = new UndertowServletWebServerFactory();
		factory.addBuilderCustomizers(builder ->
				builder.setServerOption(UndertowOptions.ENABLE_HTTP2, true)
						.setServerOption(UndertowOptions.HTTP2_SETTINGS_ENABLE_PUSH, true)
		);
		return factory;
	}

//	/**
//	 * 需要JDK的版本是1.9
//	 */
//	@Bean
//	@ConditionalOnClass(Tomcat.class)
//	public TomcatServletWebServerFactory tomcatServletWebServerFactory() {
//		return null;
//	}
}
