//package com.xiongyayun.athena.oauth2.client.config;
//
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
///**
// * SecurityConfig
// *
// * @author: Yayun.Xiong
// * @date: 2019-08-08
// */
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http
//				.authorizeRequests()
//				.anyRequest().authenticated()
//				.and()
//				.formLogin()
//				.loginPage("/login");
//	}
//
//}
