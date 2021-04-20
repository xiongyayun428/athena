package com.xiongyayun.athena.system.modules.user.auth;

/**
 * CommonAuthentication
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/3/15
 */
public interface CommonAuthentication extends Authentication {

	/**
	 * 获取用户名
	 * @return	用户名
	 */
	String getUserName();

	/**
	 * 获取密码
	 * @return	密码
	 */
	String getPassWord();
}
