package com.xiongyayun.athena.components.druid.autoconfigure;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.alibaba.druid.spring.boot.autoconfigure.properties.DruidStatProperties;
import com.xiongyayun.athena.components.druid.RemoveDruidAdFilter;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 去掉druid底部广告
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/4/28
 */
@Configuration
@ConditionalOnWebApplication
@AutoConfigureAfter(DruidDataSourceAutoConfigure.class)
@ConditionalOnProperty(name = "spring.datasource.druid.stat-view-servlet.enabled", havingValue = "true", matchIfMissing = true)
public class RemoveDruidAdAutoConfigure {

	/**
	 * 方法名: removeDruidAdFilterRegistrationBean
	 * 方法描述:  除去页面底部的广告
	 * @param druidStatProperties
	 * @return org.springframework.boot.web.servlet.FilterRegistrationBean
	 * @throws
	 */
	@Bean
	@ConditionalOnBean(DruidStatProperties.class)
	public FilterRegistrationBean<RemoveDruidAdFilter> removeDruidAdFilterRegistrationBean(DruidStatProperties druidStatProperties) {
		// 获取web监控页面的参数
		DruidStatProperties.StatViewServlet config = druidStatProperties.getStatViewServlet();
		// 提取common.js的配置路径
		String pattern = config.getUrlPattern() != null ? config.getUrlPattern() : "/druid/*";
		String commonJsPattern = pattern.replaceAll("\\*", "js/common.js");

		FilterRegistrationBean<RemoveDruidAdFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new RemoveDruidAdFilter());
		registrationBean.addUrlPatterns(commonJsPattern);
		return registrationBean;
	}
}
