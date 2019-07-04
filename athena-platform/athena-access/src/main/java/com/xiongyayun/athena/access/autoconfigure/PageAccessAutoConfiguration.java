package com.xiongyayun.athena.access.autoconfigure;

import com.xiongyayun.athena.access.properties.PageAccessProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * PageAccessAutoConfiguration
 *
 * @author: Yayun.Xiong
 * @date 2019-04-22
 */
@Configuration
@EnableConfigurationProperties(PageAccessProperties.class)
public class PageAccessAutoConfiguration {
}
