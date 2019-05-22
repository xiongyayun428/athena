package com.xyy.athena.aop;

import com.xyy.athena.response.ResBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.rmi.RemoteException;
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
    private Logger logger = LoggerFactory.getLogger(getClass());

//    @ExceptionHandler(value = AuthorizationException.class)
//    public ResBody doAuthorizationException(AuthorizationException ex) {
//        logger.error("token 鉴权失败:", ex);
//        var res = new ResBody();
//        res.noAuthError(ex.getMessage());
//        return res;
//    }

    @ExceptionHandler(value = Throwable.class)
    public ResBody doThrowable(Throwable ex) {
        logger.error("Throwable:", ex);
        var res = new ResBody();
        res.isError("系统繁忙，请稍后再试");
        return res;
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResBody doHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        logger.error("HttpRequestMethodNotSupportedException:", ex);
        var res = new ResBody();
        res.isError("不支持的方法");
        return res;
    }
    @ExceptionHandler(value = NoHandlerFoundException.class)
    public ResBody doNoHandlerFoundException(NoHandlerFoundException ex) {
        logger.error("NoHandlerFoundException:", ex);
        var res = new ResBody();
        res.isError("无效的请求路径");
        return res;
    }

    @ExceptionHandler(value = SQLException.class)
    public ResBody doSQLException(SQLException ex) {
        logger.error("SQLException:", ex);
        var res = new ResBody();
        res.isError("数据库操作异常，请联系管理员");
        return res;
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResBody doIllegalArgumentException(IllegalArgumentException ex) {
        logger.error("IllegalArgumentException:", ex);
        var res = new ResBody();
        res.isError(ex.getMessage());
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
        var res = new ResBody();
        res.isError(e.getMessage());
        return res;
    }

//    @ExceptionHandler(value = NoRecordException.class)
//    public ResBody doNoRecordException(NoRecordException ex) {
//        var res = new ResBody();
//        res.businessNoRecordError(ex.getMessage());
//        return res;
//    }

    @ExceptionHandler(value = RemoteException.class)
    public ResBody doRemoteException(RemoteException ex) {
        logger.error("RemoteException:", ex);
        var res = new ResBody();
        res.isError(ex.getMessage());
        return res;
    }

//    @ExceptionHandler(value = NotAllowedException.class)
//    public ResBody doNotAllowedException(NotAllowedException ex) {
//        logger.error("NotAllowedException:", ex);
//        var res = new ResBody();
//        res.businessNotAllowedError(ex.getMessage());
//        return res;
//    }

    @ExceptionHandler(value = BindException.class)
    public ResBody doBindException(BindException ex) {
        logger.error("BindException:", ex);
        var res = new ResBody();
        res.isError(ex.getFieldError().getDefaultMessage());
        return res;
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResBody doMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        logger.error("MethodArgumentNotValidException:", ex);
        var res = new ResBody();
        res.isError(ex.getBindingResult().getFieldError().getDefaultMessage());
        return res;
    }

}
