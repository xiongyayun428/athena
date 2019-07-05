package com.xiongyayun.athena.oauth2.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义OAuth2异常提示
 *
 * @author: Yayun.Xiong
 * @date: 2019-07-01
 */
@Component("athenaWebResponseExceptionTranslator")
public class AthenaWebResponseExceptionTranslator implements WebResponseExceptionTranslator{
	@Override
	public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
		OAuth2Exception oAuth2Exception = (OAuth2Exception) e;
		return ResponseEntity
				.status(oAuth2Exception.getHttpErrorCode())
				.body(new AthenaOAuth2Exception(oAuth2Exception.getMessage()));
	}
}
