//package com.xiongyayun.athena.spring.boot.oauth2.server.exception;
//
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
//
///**
// * AthenaSecurityExpressionHandler
// *
// * @author Yayun.Xiong
// * @date 2019-07-08
// */
//@Configuration
//public class AthenaSecurityExpressionHandler extends OAuth2WebSecurityExpressionHandler {
//	@Bean
//	public OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler(ApplicationContext applicationContext) {
//		OAuth2WebSecurityExpressionHandler expressionHandler = new OAuth2WebSecurityExpressionHandler();
//		expressionHandler.setApplicationContext(applicationContext);
//		return expressionHandler;
//	}
//}
