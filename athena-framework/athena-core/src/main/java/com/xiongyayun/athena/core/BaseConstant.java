package com.xiongyayun.athena.core;

/**
 * BaseConstant
 *
 * @author 熊亚运
 * @date 2019-06-17
 */
public class BaseConstant implements Constant {
    public static final String ACCESS_TOKEN = "Access-Token";
	/** 正则表达式：包括中文 */
    public static final String REGEX_MATCHES_CHINESE = "^.*[\u4E00-\u9FA5]+.*$";
}
