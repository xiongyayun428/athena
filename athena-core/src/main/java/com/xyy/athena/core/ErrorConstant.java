package com.xyy.athena.core;

/**
 * Error
 *
 * @author Yayun.Xiong
 * @date 2019-05-25
 */
public class ErrorConstant implements Constant {
    /** 系统繁忙，请稍后再试 */
    public static final String SYSTEM_ERROR_UNDEFINED = "system.error_undefined";
    /** 系统繁忙，未捕获异常 */
    public static final String SYSTEM_ERROR_UNCAUGHT = "system.error_uncaught";

    public static final String HTTP_REQUEST_METHOD_NOT_SUPPORTED = "http.request_method_not_supported";
    public static final String SYSTEM_NO_HANDLER_FOUND = "system.no_handler_found";
    public static final String SYSTEM_SQL = "system.sql";
    public static final String SYSTEM_ILLEGAL_ARGUMENT = "system.illegal_argument";
    public static final String SYSTEM_BAD_SQL_GRAMMAR = "system.bad_sql_grammar";
    public static final String SYSTEM_BIND = "system.bind";
    public static final String SYSTEM_METHOD_ARGUMENT_NOT_VALID = "system.method_argument_not_valid";
}
