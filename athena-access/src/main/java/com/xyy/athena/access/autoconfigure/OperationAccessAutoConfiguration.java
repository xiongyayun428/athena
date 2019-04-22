package com.xyy.athena.access.autoconfigure;

import com.xyy.athena.access.properties.OperationAccessProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * OperationAccessAutoConfiguration
 *
 * @author Yayun.Xiong
 * @date 2019-04-22
 */
@Configuration
@EnableConfigurationProperties(OperationAccessProperties.class)
public class OperationAccessAutoConfiguration {
}
