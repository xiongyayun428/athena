package com.xiongyayun.athena.auth;

import sun.security.provider.MD5;

/**
 * PasswordEncryptionMethod
 *
 * @author: Yayun.Xiong
 * @date: 2020/5/11
 */
public enum PasswordEncryptionMethod {
	/**
	 * BASE64加密
	 */
	BASE64,
	/**
	 * RSA加密
	 */
	RSA,
	/**
	 * MD5加密
	 */
	MD5
}
