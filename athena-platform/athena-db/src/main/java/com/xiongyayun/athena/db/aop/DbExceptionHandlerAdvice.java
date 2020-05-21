package com.xiongyayun.athena.db.aop;

import com.xiongyayun.athena.core.ErrorConstant;
import com.xiongyayun.athena.core.aop.ExceptionHandlerAdvice;
import com.xiongyayun.athena.core.i18n.I18nService;
import com.xiongyayun.athena.core.response.ResBody;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
public class DbExceptionHandlerAdvice extends ExceptionHandlerAdvice {

	public DbExceptionHandlerAdvice(I18nService i18nService) {
		super(i18nService);
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
	@ExceptionHandler(TooManyResultsException.class)
	public ResBody catchTooManyResultsException(TooManyResultsException e) {
		return translate(ErrorConstant.TOO_MANY_RESULTS_EXCEPTION, new Object[] {e.getMessage()}, null, e);
	}

}
