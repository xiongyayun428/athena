package com.xiongyayun.athena.access.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * RoleProperties
 *
 * @author: Yayun.Xiong
 * @date 2019-04-22
 *  //如果这里添加了注解那么在自动配置类的时候就不用添加@enableConfigurationProperties(HelloProperties.class)注解.
 */

@Component
@ConfigurationProperties(prefix = "access.data")
public class DataAccessProperties {
}
