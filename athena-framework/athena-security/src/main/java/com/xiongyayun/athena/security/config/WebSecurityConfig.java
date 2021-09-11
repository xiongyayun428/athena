package com.xiongyayun.athena.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * DruidMonitorConfig
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/4/28
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	private final static String[] AUTHORIZED_URL_WHITELIST = {
			"/favicon.ico",
			"/actuator/**",
			"/webjars/**",
			"/swagger-ui",
			"/swagger-resources/**",
			"/*/api-docs",
			"/doc.html",

//			"/instances",
//			"/actuator/**"
	};

	@Bean
	public SecurityFilterChain commonSecurityFilterChain(HttpSecurity http) throws Exception {
		http
				.authorizeRequests(authorize -> authorize
						.mvcMatchers("/druid/**").permitAll()
						.antMatchers(AUTHORIZED_URL_WHITELIST).permitAll()
						.anyRequest().authenticated()
				)
				.csrf(srf -> srf.disable()
//						.requireCsrfProtectionMatcher(request -> {
//							String servletPath = request.getServletPath();
//							System.out.println("+++++++++++++++++++++++>" + servletPath + "[" + request.getMethod() + "]");
//							if (!CsrfFilter.DEFAULT_CSRF_MATCHER.matches(request)) {
//								return false;
//							}
//							return !servletPath.contains("/druid");
//						})
//						.ignoringAntMatchers(
//								"/instances",
//								"/actuator/**"
//						)
				)
		;
		return http.build();
	}

	/**
	 * 全局请求忽略规则配置
	 * @return
	 */
	@Bean
	public WebSecurityCustomizer ignoringCustomizer() {
		return web -> web.ignoring().antMatchers(HttpMethod.GET, AUTHORIZED_URL_WHITELIST);
	}
}
