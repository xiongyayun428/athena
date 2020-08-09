package com.xiongyayun.athena.core.aop;

import com.xiongyayun.athena.core.ErrorConstant;
import com.xiongyayun.athena.core.exception.AthenaException;
import com.xiongyayun.athena.core.exception.AthenaRuntimeException;
import com.xiongyayun.athena.core.i18n.I18nService;
import com.xiongyayun.athena.core.response.ResBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ExceptionHandlerAdvice
 *
 * @author: 熊亚运
 * @date: 2019-05-21
 */
@RestControllerAdvice
public class ExceptionHandlerAdvice {
	protected static final Logger log = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);
    private final I18nService i18nService;
    private static Map<String, KeyValue> ERROR_CACHE = new HashMap<>();

    @Autowired
	public ExceptionHandlerAdvice(I18nService i18nService) {
		this.i18nService = i18nService;
	}

	@ExceptionHandler(Throwable.class)
    public ResBody catchThrowable(Throwable e) {
        return translate(ErrorConstant.SYSTEM_ERROR_UNCAUGHT, null, null, e);
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResBody catchHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return translate(ErrorConstant.HTTP_REQUEST_METHOD_NOT_SUPPORTED, new Object[] {e.getMethod()}, null, e);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResBody catchNoHandlerFoundException(NoHandlerFoundException e) {
        return translate(ErrorConstant.SYSTEM_NO_HANDLER_FOUND, new Object[] {e.getHttpMethod(), e.getRequestURL()}, null, e);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResBody catchIllegalArgumentException(IllegalArgumentException e) {
        return translate(ErrorConstant.ILLEGAL_ARGUMENT_EXCEPTION, new Object[] {e.getMessage()}, null, e);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResBody catchHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        return translate(ErrorConstant.HTTP_MEDIA_TYPE_NOT_SUPPORTED_EXCEPTION, new Object[] {e.getContentType()}, null, e);
    }

    @ExceptionHandler(BindException.class)
    public ResBody catchBindException(BindException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        Object[] args = {fieldError.getField(), fieldError.getDefaultMessage()};
        return translate(ErrorConstant.BIND_EXCEPTION, args, fieldError.getDefaultMessage(), e);
    }

    @ExceptionHandler(ClassCastException.class)
    public ResBody catchBindException(ClassCastException e) {
        return translate(ErrorConstant.CLASS_CAST_EXCEPTION, null, null, e);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResBody catchMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        Object[] args = {fieldError.getField(), fieldError.getDefaultMessage()};
        return translate(ErrorConstant.METHOD_ARGUMENT_NOT_VALID_EXCEPTION, args, fieldError.getDefaultMessage(), e);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResBody catchHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return translate(ErrorConstant.HTTP_MESSAGE_NOT_READABLE_EXCEPTION, null, null, e);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResBody catchMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return translate(ErrorConstant.MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION, new Object[] {e.getParameterName(), e.getParameterType()}, null, e);
    }

    @ExceptionHandler(TypeMismatchException.class)
    public ResBody catchTypeMismatchException(TypeMismatchException e) {
        return translate(ErrorConstant.TYPE_MISMATCH_EXCEPTION, new Object[] {e.getPropertyName(), e.getRequiredType()}, null, e);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResBody catchConstraintViolationException(ConstraintViolationException e) {
		String msg = e.getConstraintViolations().stream()
				.map( cv -> cv == null ? "null" : cv.getMessage() )
//				.map( cv -> cv == null ? "null" : cv.getPropertyPath() + ": " + cv.getMessage() )
				.collect( Collectors.joining( ", " ) );
        return translate(ErrorConstant.CONSTRAINT_VIOLATION_EXCEPTION, new Object[] {msg}, null, e);
    }

    @ExceptionHandler(AthenaRuntimeException.class)
    public ResBody catchAthenaRuntimeException(AthenaRuntimeException e) {
		if (!StringUtils.isEmpty(e.getCode())) {
			return new ResBody(e.getCode(), e.getMessageKey());
		}
        return translate(e.getMessage(), e.getArgs(), null, e);
    }

    @ExceptionHandler(AthenaException.class)
    public ResBody catchAthenaException(AthenaException e) {
		if (!StringUtils.isEmpty(e.getCode())) {
			return new ResBody(e.getCode(), e.getMessageKey());
		}
        return translate(e.getMessage(), e.getArgs(), null, e);
    }

    protected ResBody translate(String code, @Nullable Object[] args, @Nullable String defaultMsg, @Nullable Throwable e) {
        final ResBody resBody = new ResBody();
        if (i18nService == null) {
        	throw new RuntimeException("请配置i18nService");
		}
		KeyValue cache = ERROR_CACHE.get(code);
        if (cache != null) {
			resBody.withCode(cache.getKey()).withMsg(cache.getValue());
		} else {
        	// 翻译后
			String i18nValue = i18nService.get(code, args, defaultMsg);
			Object val = null;
			if (!StringUtils.isEmpty(i18nValue)) {
				String[] separator = {" ", ",", "，", "|", ":", "：", "^", ";", "；", "/"};
				String[] i18nValueArray = i18nValue.split("");
				val = Arrays.asList(i18nValueArray).stream()
						.filter(v -> Arrays.asList(separator).stream().anyMatch(v::equals))
						.findFirst()
						.orElse(null)
						;
			}
			// 翻译后存在错误码和错误消息
			if (val != null) {
				int s = i18nValue.indexOf(String.valueOf(val));
				resBody.withCode(i18nValue.substring(0, s)).withMsg(i18nValue.substring(s + 1));
			} else {
				// 没找到翻译
				if (StringUtils.isEmpty(i18nValue)) {
					log.warn("请配置错误码和错误信息: {}", code);
					resBody.setRtnMsg(code);
				} else {
					log.warn("错误码和错误信息配置错误: {}", code);
					resBody.setRtnMsg(i18nValue);
				}
				resBody.setRtnCode("999999");
			}
			ERROR_CACHE.put(code, new KeyValue(resBody.getRtnCode(), resBody.getRtnMsg()));
		}
        if (e != null) {
            log.error(resBody.getRtnMsg(), e);
        } else {
            log.error(resBody.getRtnMsg());
        }
        return resBody;
    }

    class KeyValue {
    	private String key;
    	private String value;
    	public KeyValue(String key, String value) {
    		this.key = key;
    		this.value = value;
		}

		public String getKey() {
    		return this.key;
		}

		public String getValue() {
			return value;
		}
	}
}
