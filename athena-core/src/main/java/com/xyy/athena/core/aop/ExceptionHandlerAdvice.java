package com.xyy.athena.core.aop;

import com.xyy.athena.core.ErrorConstant;
import com.xyy.athena.core.i18n.I18nService;
import com.xyy.athena.core.response.ResBody;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
@RestControllerAdvice
@ResponseBody
public class ExceptionHandlerAdvice {
    private Log logger = LogFactory.getLog(getClass());

    @Autowired
    private I18nService i18nService;

//    @ExceptionHandler(value = AuthorizationException.class)
//    public ResBody doAuthorizationException(AuthorizationException ex) {
//        logger.error("token 鉴权失败:", ex);
//        var res = new ResBody();
//        res.noAuthError(ex.getMessage());
//        return res;
//    }

    @ExceptionHandler(value = Throwable.class)
    public ResBody throwable(Throwable e) {
        return new ResBody(i18nService, ErrorConstant.SYSTEM_ERROR_UNCAUGHT, e);
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResBody doHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
//        res.isError("不支持的方法");
        return new ResBody(i18nService, ErrorConstant.HTTP_REQUEST_METHOD_NOT_SUPPORTED, e);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = NoHandlerFoundException.class)
    public ResBody doNoHandlerFoundException(NoHandlerFoundException ex) {
        logger.error("NoHandlerFoundException:", ex);
        var res = new ResBody(i18nService, ErrorConstant.SYSTEM_NO_HANDLER_FOUND, ex);
//        res.isError("无效的请求路径");
        return res;
    }

    @ExceptionHandler(value = SQLException.class)
    public ResBody doSQLException(SQLException ex) {
        logger.error("SQLException:", ex);
        var res = new ResBody(i18nService, ErrorConstant.SYSTEM_SQL, ex);
//        res.isError("数据库操作异常，请联系管理员");
        return res;
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResBody doIllegalArgumentException(IllegalArgumentException ex) {
        logger.error("IllegalArgumentException:", ex);
        var res = new ResBody(i18nService, ErrorConstant.SYSTEM_ILLEGAL_ARGUMENT, ex);
//        res.isError(ex.getMessage());
        return res;
    }

    /**
     * sql语句异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = BadSqlGrammarException.class)
    public ResBody doBadSqlGrammarException(BadSqlGrammarException e) {
        logger.error("BadSqlGrammarException:", e);
        var res = new ResBody(i18nService, ErrorConstant.SYSTEM_BAD_SQL_GRAMMAR, e);
//        res.isError(e.getMessage());
        return res;
    }

//    @ExceptionHandler(value = NoRecordException.class)
//    public ResBody doNoRecordException(NoRecordException ex) {
//        var res = new ResBody();
//        res.businessNoRecordError(ex.getMessage());
//        return res;
//    }

//    @ExceptionHandler(value = RemoteException.class)
//    public ResBody doRemoteException(RemoteException ex) {
//        logger.error("RemoteException:", ex);
//        var res = new ResBody();
//        res.isError(ex.getMessage());
//        return res;
//    }

//    @ExceptionHandler(value = NotAllowedException.class)
//    public ResBody doNotAllowedException(NotAllowedException ex) {
//        logger.error("NotAllowedException:", ex);
//        var res = new ResBody();
//        res.businessNotAllowedError(ex.getMessage());
//        return res;
//    }

    @ExceptionHandler(value = BindException.class)
    public ResBody bindException(BindException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        Object[] args = {fieldError.getField(), fieldError.getDefaultMessage()};
        var res = new ResBody(i18nService, ErrorConstant.SYSTEM_BIND, args, fieldError.getDefaultMessage());
        return res;
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResBody methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        Object[] args = {fieldError.getField(), fieldError.getDefaultMessage()};
        return new ResBody(i18nService, ErrorConstant.SYSTEM_METHOD_ARGUMENT_NOT_VALID, args, fieldError.getDefaultMessage());
    }

}
