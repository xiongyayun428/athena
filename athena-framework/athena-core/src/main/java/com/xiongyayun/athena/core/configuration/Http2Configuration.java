package com.xiongyayun.athena.core.configuration;

import io.undertow.UndertowOptions;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * HTTP2支持
 *
 * @author: 熊亚运
 * @date: 2019-05-22
 */
@Configuration
public class Http2Configuration {

    @Bean
    UndertowServletWebServerFactory undertowServletWebServerFactory() {
        UndertowServletWebServerFactory factory = new UndertowServletWebServerFactory();
        factory.addBuilderCustomizers(builder ->
            builder.setServerOption(UndertowOptions.ENABLE_HTTP2, true)
                    .setServerOption(UndertowOptions.HTTP2_SETTINGS_ENABLE_PUSH, true)
        );
        return factory;
    }
}
