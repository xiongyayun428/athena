package com.xiongyayun.athena.oauth2.server.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;

/**
 * 自定义OAuth2异常提示
 *
 * @author: Yayun.Xiong
 * @date: 2019-07-01
 */
@Component
public class AthenaWebResponseExceptionTranslator implements WebResponseExceptionTranslator{
	@Override
	public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
		System.out.println("11111111111111111");
		System.out.println(e.getMessage());
		System.out.println("11111111111111111");
		OAuth2Exception oAuth2Exception = (OAuth2Exception) e;
		return ResponseEntity
				.status(oAuth2Exception.getHttpErrorCode())
				.body(new AthenaOAuth2Exception(oAuth2Exception.getMessage()));
	}
}
