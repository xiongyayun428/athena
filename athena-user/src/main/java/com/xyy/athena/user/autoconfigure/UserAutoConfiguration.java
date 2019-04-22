package com.xyy.athena.user.autoconfigure;

import com.xyy.athena.user.properties.UserProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * UserAutoConfiguration
 *
 * @author Yayun.Xiong
 * @date 2019-04-22
 */
@Configuration
@EnableConfigurationProperties(UserProperties.class)
public class UserAutoConfiguration {
}
