package com.xiongyayun.athena.system.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * ResourceServerConfig
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/4/25
 */
@EnableWebSecurity
public class OAuth2ResourceServerSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.mvcMatcher("/sys/**")
				.authorizeRequests()
				.mvcMatchers("/sys/**").access("hasAuthority('SCOPE_message.read')")
				.and()
				.oauth2ResourceServer()
				.jwt();
	}
}
