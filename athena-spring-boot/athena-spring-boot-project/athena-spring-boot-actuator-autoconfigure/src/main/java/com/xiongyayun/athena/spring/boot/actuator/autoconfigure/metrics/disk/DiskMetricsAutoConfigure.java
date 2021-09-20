package com.xiongyayun.athena.spring.boot.actuator.autoconfigure.metrics.disk;

import com.xiongyayun.athena.spring.boot.actuator.metrics.DiskMetrics;
import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnAvailableEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * DiskMetricsAutoConfigure
 *
 * @author Yayun.Xiong
 * @date 2021/9/20
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnAvailableEndpoint(endpoint = DiskMetrics.class)
public class DiskMetricsAutoConfigure {

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnBean({DiskMetrics.class})
	public DiskMetrics diskMetrics() {
		return new DiskMetrics();
	}
}
