package com.xiongyayun.athena.core.configuration;

import com.xiongyayun.athena.core.annotation.RequestJsonArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * WebMvcConfig
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2020/11/27
 */
@Configuration
//@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new RequestJsonArgumentResolver());
	}

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		// 清除String转换器
		converters.removeIf(converter -> converter instanceof StringHttpMessageConverter);
	}
}
