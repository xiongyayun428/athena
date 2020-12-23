//package com.xiongyayun.athena.spring.boot.oauth2.server.configuration;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
///**
// * 配置所有请求的安全验证
// *
// * @author Yayun.Xiong
// * @date 2019-07-01
// */
//@Slf4j
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
//
////	@Autowired
////	private UserDetailsService userDetailsService;
//
//	@Bean
//	public BCryptPasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//
//	/**
//	 * 不定义没有password grant_type
//	 *
//	 * @return
//	 * @throws Exception
//	 */
//	@Bean
//	@Override
//	public AuthenticationManager authenticationManagerBean() throws Exception {
//		return super.authenticationManagerBean();
//	}
//
////	@Override
////	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////		auth
////			.userDetailsService(userDetailsService)
////			.passwordEncoder(passwordEncoder());
////	}
//
//}
