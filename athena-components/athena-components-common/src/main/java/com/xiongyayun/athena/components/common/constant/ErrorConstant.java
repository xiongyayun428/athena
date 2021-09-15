package com.xiongyayun.athena.components.common.constant;

import com.xiongyayun.athena.components.common.constant.Constant;

/**
 * Error
 *
 * @author Yayun.Xiong
 * @date 2019-05-25
 */
public interface ErrorConstant extends Constant {
    /** 系统繁忙，请稍后再试 */
    String SYSTEM_ERROR_UNDEFINED = "athena.error.undefined";
    /** 系统繁忙，未捕获异常 */
    String SYSTEM_ERROR_UNCAUGHT = "system.error_uncaught";

    String NULL_POINTER_EXCEPTION = "NullPointerException";

    String HTTP_REQUEST_METHOD_NOT_SUPPORTED = "HttpRequestMethodNotSupportedException";
    String SYSTEM_NO_HANDLER_FOUND = "NoHandlerFoundException";
    String SQL_EXCEPTION = "SQLException";
    String TOO_MANY_RESULTS_EXCEPTION = "TooManyResultsException";
    String ILLEGAL_ARGUMENT_EXCEPTION = "IllegalArgumentException";
    String BAD_SQL_GRAMMAR_EXCEPTION = "BadSqlGrammarException";
    String BIND_EXCEPTION = "BindException";
    String CONSTRAINT_VIOLATION_EXCEPTION = "ConstraintViolationException";
    String CLASS_CAST_EXCEPTION = "ClassCastException";
    String METHOD_ARGUMENT_NOT_VALID_EXCEPTION = "MethodArgumentNotValidException";
    String HTTP_MESSAGE_NOT_READABLE_EXCEPTION = "HttpMessageNotReadableException";
    String MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION = "MissingServletRequestParameterException";
    String METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTION = "MethodArgumentTypeMismatchException";
    String TYPE_MISMATCH_EXCEPTION = "TypeMismatchException";
    String HTTP_MEDIA_TYPE_NOT_SUPPORTED_EXCEPTION = "HttpMediaTypeNotSupportedException";
    String DATA_INTEGRITY_VIOLATION_EXCEPTION = "DataIntegrityViolationException";
    String MISSING_SERVLET_REQUEST_PART_EXCEPTION = "MissingServletRequestPartException";
    String MY_BATIS_SYSTEM_EXCEPTION = "MyBatisSystemException";
}
