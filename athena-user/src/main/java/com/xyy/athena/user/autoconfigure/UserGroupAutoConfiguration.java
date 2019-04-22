package com.xyy.athena.user.autoconfigure;

import com.xyy.athena.user.properties.UserGroupProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * UserGroupAutoConfiguration
 *
 * @author Yayun.Xiong
 * @date 2019-04-22
 */
@Configuration
@EnableConfigurationProperties(UserGroupProperties.class)
public class UserGroupAutoConfiguration {
}
