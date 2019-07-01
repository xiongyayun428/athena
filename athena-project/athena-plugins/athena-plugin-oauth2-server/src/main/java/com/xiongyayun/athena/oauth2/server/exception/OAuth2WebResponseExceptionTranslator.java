package com.xiongyayun.athena.oauth2.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义OAuth2异常提示
 *
 * @author: Yayun.Xiong
 * @date: 2019-07-01
 */
public class OAuth2WebResponseExceptionTranslator implements WebResponseExceptionTranslator{
	@Override
	public ResponseEntity translate(Exception e) throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//		ResultBody responseData = OpenGlobalExceptionHandler.resolveOauthException(e,request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(request.getRequestURI());
	}
}
