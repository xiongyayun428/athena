package com.xiongyayun.athena.components.autoconfigure.mybatis;

import com.xiongyayun.athena.components.common.ResBody;
import com.xiongyayun.athena.components.common.exception.TranslateException;
import com.xiongyayun.athena.components.common.i18n.I18nService;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;

/**
 * MyBatisExceptionHandlerAdvice
 *
 * @author Yayun.Xiong
 * @date 2021/9/14
 */
@RestControllerAdvice
public class MyBatisExceptionHandlerAdvice {

	@Resource
	private I18nService i18nService;

	@ExceptionHandler(MyBatisSystemException.class)
	public ResBody<?> handleException(MyBatisSystemException e) {
		return new TranslateException(i18nService).translate(e.getClass().getName(), null, e.getMessage(), e);
	}
}
