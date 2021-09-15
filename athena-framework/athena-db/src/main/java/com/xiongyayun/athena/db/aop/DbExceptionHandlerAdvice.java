package com.xiongyayun.athena.db.aop;

import com.xiongyayun.athena.components.common.constant.ErrorConstant;
import com.xiongyayun.athena.components.common.exception.ExceptionHandlerAdvice;
import com.xiongyayun.athena.components.common.i18n.I18nService;
import com.xiongyayun.athena.components.common.ResBody;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

/**
 * ExceptionHandlerAdvice
 *
 * @author 熊亚运
 * @date 2019-05-21
 */
@Order(Ordered.LOWEST_PRECEDENCE - 1)
@RestControllerAdvice
public class DbExceptionHandlerAdvice extends ExceptionHandlerAdvice {

	public DbExceptionHandlerAdvice(I18nService i18nService) {
		super(i18nService);
	}

	@ExceptionHandler(BadSqlGrammarException.class)
	public ResBody<?> handleException(BadSqlGrammarException e) {
		SQLException se = e.getSQLException();
		return translate(ErrorConstant.BAD_SQL_GRAMMAR_EXCEPTION, new Object[] {se.getMessage(), se.getErrorCode(), se.getSQLState()}, se.getLocalizedMessage(), e);
	}
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResBody<?> handleException(DataIntegrityViolationException e) {
		return translate(ErrorConstant.DATA_INTEGRITY_VIOLATION_EXCEPTION, new Object[] {e.getMessage()}, e.getLocalizedMessage(), e);
	}

	@ExceptionHandler(SQLException.class)
	public ResBody<?> handleException(SQLException e) {
		return translate(ErrorConstant.SQL_EXCEPTION, new Object[] {e.getErrorCode(), e.getSQLState()}, e.getLocalizedMessage(), e);
	}
	@ExceptionHandler(TooManyResultsException.class)
	public ResBody<?> handleException(TooManyResultsException e) {
		return translate(ErrorConstant.TOO_MANY_RESULTS_EXCEPTION, new Object[] {e.getMessage()}, e.getLocalizedMessage(), e);
	}

}
