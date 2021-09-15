package com.xiongyayun.athena.core.exception.enums;

/**
 * 异常枚举格式规范
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/4/22
 */
public interface AthenaExceptionEnum {
	/**
	 * 获取异常的状态码
	 * @return	状态码
	 */
	String getCode();

	/**
	 * 获取异常的提示信息
	 * @return	提示信息
	 */
	String getMessage();
}
