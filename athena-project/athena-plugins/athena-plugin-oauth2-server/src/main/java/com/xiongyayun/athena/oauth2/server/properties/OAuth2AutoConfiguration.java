package com.xiongyayun.athena.oauth2.server.properties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * OAuth2CoreConfig
 *
 * @author: Yayun.Xiong
 * @date: 2019-07-05
 */
@Configuration
@EnableConfigurationProperties(OAuth2Properties.class)
public class OAuth2AutoConfiguration {
}
