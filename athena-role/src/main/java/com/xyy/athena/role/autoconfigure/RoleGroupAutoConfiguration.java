package com.xyy.athena.role.autoconfigure;

import com.xyy.athena.role.properties.RoleGroupProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * RoleGroupAutoConfiguration
 *
 * @author Yayun.Xiong
 * @date 2019-04-22
 */
@Configuration
@EnableConfigurationProperties(RoleGroupProperties.class)
public class RoleGroupAutoConfiguration {
}
