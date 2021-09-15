package com.xiongyayun.athena.components.common.constant;

/**
 * CacheConstant
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/3/29
 */
public interface CommonConstant extends Constant {
	String ACCESS_TOKEN = "Access-Token";
	String REFRESH_TOKEN = "Refresh-Token";
	/**
	 * 请求号在header中的唯一标识
	 */
	String REQUEST_NO_HEADER_NAME = "Request-No";

	/**
	 * 语言
	 */
	String LANGUAGE = "language";

	String REGEX_MATCHES_CHINESE = "^.*[\u4E00-\u9FA5]+.*$";
}
