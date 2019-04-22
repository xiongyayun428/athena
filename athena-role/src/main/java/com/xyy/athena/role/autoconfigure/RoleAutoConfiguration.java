package com.xyy.athena.role.autoconfigure;

import com.xyy.athena.role.properties.RoleProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * RoleAutoConfiguration
 *
 * @author Yayun.Xiong
 * @date 2019-04-22
 */
@Configuration
@EnableConfigurationProperties(RoleProperties.class)
public class RoleAutoConfiguration {
}
