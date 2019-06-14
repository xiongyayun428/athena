package com.xyy.athena.core.aop;

import com.xyy.athena.core.ErrorConstant;
import com.xyy.athena.core.exception.AthenaException;
import com.xyy.athena.core.exception.AthenaRuntimeException;
import com.xyy.athena.core.i18n.I18nService;
import com.xyy.athena.core.response.ResBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
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

import java.sql.SQLException;

/**
 * ExceptionHandlerAdvice
 *
 * @author: 熊亚运
 * @date: 2019-05-21
 */
@Slf4j
@RestControllerAdvice
@ResponseBody
public class ExceptionHandlerAdvice {

    @Autowired
    private I18nService i18nService;

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

    @ExceptionHandler(BadSqlGrammarException.class)
    public ResBody catchBadSqlGrammarException(BadSqlGrammarException e) {
        SQLException se = e.getSQLException();
        return translate(ErrorConstant.BAD_SQL_GRAMMAR_EXCEPTION, new Object[] {se.getErrorCode(), se.getSQLState()}, null, e);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResBody catchDataIntegrityViolationException(DataIntegrityViolationException e) {
        return translate(ErrorConstant.DATA_INTEGRITY_VIOLATION_EXCEPTION, new Object[] {e.getMessage()}, null, e);
    }

    @ExceptionHandler(SQLException.class)
    public ResBody catchSQLException(SQLException e) {
        return translate(ErrorConstant.SQL_EXCEPTION, new Object[] {e.getErrorCode(), e.getSQLState()}, null, e);
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

    @ExceptionHandler(AthenaRuntimeException.class)
    public ResBody catchAthenaRuntimeException(AthenaRuntimeException e) {
        return translate(e.getMessage(), e.getArgs(), null, e);
    }

    @ExceptionHandler(AthenaException.class)
    public ResBody catchAthenaException(AthenaException e) {
        return translate(e.getMessage(), e.getArgs(), null, e);
    }

    private ResBody translate(String code, @Nullable Object[] args, @Nullable String msg, @Nullable Throwable e) {
        ResBody resBody;
        if (i18nService != null) {
            String rtnMsg = i18nService.get(code, args, msg);
            if (StringUtils.isEmpty(rtnMsg)) {
                // TODO 错误码不知道怎么定义
                resBody = new ResBody("", code);
            } else {
                resBody = new ResBody(code, rtnMsg);
            }
        } else {
            resBody = new ResBody(code, msg);
        }
//        var resBody = new ResBody(code).withCode(code).withArgs(args).withMsg(msg).withI18nService(i18nService);
        if (e != null) {
            log.error(resBody.getRtnMsg(), e);
        } else {
            log.error(resBody.getRtnMsg());
        }
        return resBody;
    }

}
