//package com.xyy.athena.admin.server;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@Configuration
//public class SecurityWebConfig extends WebSecurityConfigurerAdapter {
//	@Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.httpBasic().and()
//                .authorizeRequests().antMatchers("/actuator/**").authenticated()
//                .anyRequest().permitAll();
//    }
//}
