package com.xiongyayun.athena.application.user.constant;

/**
 * CacheConstant
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/3/29
 */
public class Constant {
	/**
	 * 缓存用户信息
	 */
	public static final String SYS_USERS_CACHE = "sys:cache:user";

	public static final String USERNAME_PASSWORD_ERROR = "用户名或密码错误!";
	public static final String CREDENTIALS_EXPIRED = "该账户的登录凭证已过期，请重新登录!";
	public static final String ACCOUNT_DISABLED = "该账户已被禁用，请联系管理员!";
	public static final String ACCOUNT_LOCKED = "该账号已被锁定，请联系管理员!";
	public static final String ACCOUNT_EXPIRED = "该账号已过期，请联系管理员!";
}
