package com.xiongyayun.athena.access.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * RoleProperties
 *
 * @author: Yayun.Xiong
 * @date 2019-04-22
 */
//@Component //如果这里添加了注解那么在自动配置类的时候就不用添加@enableConfigurationProperties(HelloProperties.class)注解.
@ConfigurationProperties(prefix = "access.data")
public class DataAccessProperties {
}
