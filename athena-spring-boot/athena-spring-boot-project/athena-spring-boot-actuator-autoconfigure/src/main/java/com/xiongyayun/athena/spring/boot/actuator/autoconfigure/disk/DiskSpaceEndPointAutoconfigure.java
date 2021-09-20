package com.xiongyayun.athena.spring.boot.actuator.autoconfigure.disk;

import com.xiongyayun.athena.spring.boot.actuator.endpoint.DiskSpaceEndPoint;
import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnAvailableEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * DiskSpaceEndPointAutoconfigure
 *
 * @author Yayun.Xiong
 * @date 2021/9/20
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnAvailableEndpoint(endpoint = DiskSpaceEndPoint.class)
@EnableConfigurationProperties({DiskSpaceEndPointProperties.class})
public class DiskSpaceEndPointAutoconfigure {

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnBean({DiskSpaceEndPoint.class})
	public DiskSpaceEndPoint diskSpaceEndPoint() {
		return new DiskSpaceEndPoint();
	}
}
