package com.xiongyayun.athena.components.common.exception;

import com.xiongyayun.athena.components.common.ResBody;
import com.xiongyayun.athena.components.common.constant.ErrorConstant;
import com.xiongyayun.athena.components.common.i18n.I18nService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * ExceptionHandlerAdvice
 *
 * @author 熊亚运
 * @date 2019-05-21
 */
@Order()
@RestControllerAdvice
public class ExceptionHandlerAdvice {
	protected static final Logger log = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);
    private final I18nService i18nService;

    @Autowired
	public ExceptionHandlerAdvice(I18nService i18nService) {
		this.i18nService = i18nService;
	}

	@ExceptionHandler(Throwable.class)
    public ResBody<?> handleException(Throwable e) {
        return translate(ErrorConstant.SYSTEM_ERROR_UNCAUGHT, null, e.getLocalizedMessage(), e);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResBody<?> handleException(NullPointerException e) {
        return translate(ErrorConstant.NULL_POINTER_EXCEPTION, null, e.getLocalizedMessage(), e);
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResBody<?> handleException(HttpRequestMethodNotSupportedException e) {
        return translate(ErrorConstant.HTTP_REQUEST_METHOD_NOT_SUPPORTED, new Object[] {e.getMethod()}, e.getLocalizedMessage(), e);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResBody<?> handleException(NoHandlerFoundException e) {
        return translate(ErrorConstant.SYSTEM_NO_HANDLER_FOUND, new Object[] {e.getHttpMethod(), e.getRequestURL()}, e.getLocalizedMessage(), e);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResBody<?> handleException(IllegalArgumentException e) {
        return translate(ErrorConstant.ILLEGAL_ARGUMENT_EXCEPTION, new Object[] {e.getMessage()}, e.getLocalizedMessage(), e);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResBody<?> handleException(HttpMediaTypeNotSupportedException e) {
        return translate(ErrorConstant.HTTP_MEDIA_TYPE_NOT_SUPPORTED_EXCEPTION, new Object[] {e.getContentType()}, e.getLocalizedMessage(), e);
    }

    @ExceptionHandler(BindException.class)
    public ResBody<?> catchBindException(BindException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        if (fieldError == null) {
			return translate(ErrorConstant.BIND_EXCEPTION, null, e.getMessage(), e);
		}
        Object[] args = {fieldError.getField(), fieldError.getDefaultMessage()};
        return translate(ErrorConstant.BIND_EXCEPTION, args, fieldError.getDefaultMessage(), e);
    }

    @ExceptionHandler(ClassCastException.class)
    public ResBody<?> handleException(ClassCastException e) {
        return translate(ErrorConstant.CLASS_CAST_EXCEPTION, null, e.getLocalizedMessage(), e);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResBody<?> handleException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
		if (fieldError == null) {
			return translate(ErrorConstant.METHOD_ARGUMENT_NOT_VALID_EXCEPTION, null, e.getMessage(), e);
		}
        Object[] args = {fieldError.getField(), fieldError.getDefaultMessage()};
        return translate(ErrorConstant.METHOD_ARGUMENT_NOT_VALID_EXCEPTION, args, fieldError.getDefaultMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResBody<?> handleException(HttpMessageNotReadableException e) {
        return translate(ErrorConstant.HTTP_MESSAGE_NOT_READABLE_EXCEPTION, null, e.getLocalizedMessage(), e);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResBody<?> handleException(MissingServletRequestParameterException e) {
        return translate(ErrorConstant.MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION, new Object[] {e.getParameterName(), e.getParameterType()}, e.getLocalizedMessage(), e);
    }

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResBody<?> catchMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
		return translate(ErrorConstant.METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTION, new Object[] {e.getParameter().getMethod(), e.getName(), e.getValue(), e.getMessage()}, e.getLocalizedMessage(), e);
	}

    @ExceptionHandler(TypeMismatchException.class)
    public ResBody<?> handleException(TypeMismatchException e) {
        return translate(ErrorConstant.TYPE_MISMATCH_EXCEPTION, new Object[] {e.getPropertyName(), e.getRequiredType()}, e.getLocalizedMessage(), e);
    }

	@ExceptionHandler(MissingServletRequestPartException.class)
	public ResBody<?> handleException(MissingServletRequestPartException e) {
		return translate(ErrorConstant.MISSING_SERVLET_REQUEST_PART_EXCEPTION, new Object[] {e.getRequestPartName()}, e.getMessage(), e);
	}

    @ExceptionHandler(ConstraintViolationException.class)
    public ResBody<?> handleException(ConstraintViolationException e) {
		String msg = e.getConstraintViolations().stream()
				.map( cv -> cv == null ? "null" : cv.getMessage() )
//				.map( cv -> cv == null ? "null" : cv.getPropertyPath() + ": " + cv.getMessage() )
				.collect( Collectors.joining( ", " ) );
        return translate(ErrorConstant.CONSTRAINT_VIOLATION_EXCEPTION, new Object[] {msg}, e.getLocalizedMessage(), e);
    }

    @ExceptionHandler(AthenaRuntimeException.class)
    public ResBody<?> catchAthenaRuntimeException(AthenaRuntimeException e) {
		if (StringUtils.hasLength(e.getCode())) {
			if (e.getMostSpecificCause() != null) {
				log.error(e.getDefaultMessage(), e.getMostSpecificCause());
			}
			return new ResBody<>(e.getCode(), e.getMessage());
		}
        return translate(e.getMessage(), e.getArgs(), e.getMessage(), e);
    }

    @ExceptionHandler(AthenaException.class)
    public ResBody<?> handleException(AthenaException e) {
		if (StringUtils.hasLength(e.getCode())) {
			if (e.getMostSpecificCause() != null) {
				log.error(e.getDefaultMessage(), e.getMostSpecificCause());
			}
			return new ResBody<>(e.getCode(), e.getMessage());
		}
        return translate(e.getMessage(), e.getArgs(), e.getMessage(), e);
    }

	protected ResBody<?> translate(String key, @Nullable Object[] args, @Nullable String defaultMsg) {
    	return translate(key, args, defaultMsg, null);
	}

    protected ResBody<?> translate(String key, @Nullable Object[] args, @Nullable String defaultMsg, @Nullable Throwable e) {
        return new TranslateException(this.i18nService).translate(key, args, defaultMsg, e);
    }
}
