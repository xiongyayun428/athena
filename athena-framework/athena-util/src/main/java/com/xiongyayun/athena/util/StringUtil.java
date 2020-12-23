package com.xiongyayun.athena.util;

/**
 * StringUtil
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2020/10/30
 */
public class StringUtil {

	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	public static boolean isBlank(String str) {
		return str == null || str.trim().length() == 0;
	}
}
