package com.xiongyayun.athena.core;

/**
 * Error
 *
 * @author: Yayun.Xiong
 * @date 2019-05-25
 */
public class ErrorConstant implements Constant {
    /** 系统繁忙，请稍后再试 */
    public static final String SYSTEM_ERROR_UNDEFINED = "athena.error.undefined";
    /** 系统繁忙，未捕获异常 */
    public static final String SYSTEM_ERROR_UNCAUGHT = "system.error_uncaught";

    public static final String HTTP_REQUEST_METHOD_NOT_SUPPORTED = "HttpRequestMethodNotSupportedException";
    public static final String SYSTEM_NO_HANDLER_FOUND = "NoHandlerFoundException";
    public static final String SQL_EXCEPTION = "SQLException";
    public static final String TOO_MANY_RESULTS_EXCEPTION = "TooManyResultsException";
    public static final String ILLEGAL_ARGUMENT_EXCEPTION = "IllegalArgumentException";
    public static final String BAD_SQL_GRAMMAR_EXCEPTION = "BadSqlGrammarException";
    public static final String BIND_EXCEPTION = "BindException";
    public static final String CONSTRAINT_VIOLATION_EXCEPTION = "ConstraintViolationException";
    public static final String CLASS_CAST_EXCEPTION = "ClassCastException";
    public static final String METHOD_ARGUMENT_NOT_VALID_EXCEPTION = "MethodArgumentNotValidException";
    public static final String HTTP_MESSAGE_NOT_READABLE_EXCEPTION = "HttpMessageNotReadableException";
    public static final String MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION = "MissingServletRequestParameterException";
    public static final String METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTION = "MethodArgumentTypeMismatchException";
    public static final String TYPE_MISMATCH_EXCEPTION = "TypeMismatchException";
    public static final String HTTP_MEDIA_TYPE_NOT_SUPPORTED_EXCEPTION = "HttpMediaTypeNotSupportedException";
    public static final String DATA_INTEGRITY_VIOLATION_EXCEPTION = "DataIntegrityViolationException";
    public static final String MY_BATIS_SYSTEM_EXCEPTION = "MyBatisSystemException";
}
