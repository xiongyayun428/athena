package com.xiongyayun.athena.auth;

/**
 * 密码策略
 *
 * @author: Yayun.Xiong
 * @date: 2020/5/11
 */
public class PasswordPolicy {
	/**
	 * disable 状态
	 */
	private boolean disabled;
	/**
	 * 密码加密方式
	 */
	private PasswordEncryptionMethod passwordEncryptionMethod;
	/**
	 * 密码加密密钥
	 */
	private String secret;
	/**
	 * 密码最小长度
	 */
	private Integer minLength;
	/**
	 * 密码最大长度
	 */
	private Integer maxLength;
	/**
	 * 是否需要包含数字
	 */
	private boolean requireDigit;
	/**
	 * 是否需要包含小写字母
	 */
	private boolean requireLowerCase;
	/**
	 * 是否需要包含大写字母
	 */
	private boolean requireUpperCase;
	/**
	 * 是否需要包含特殊字符
	 */
	private boolean requireSpecialCharacter;
	/**
	 * 是否需要包含数字/是否需要包含小写字母/是否需要包含大写字母/是否需要包含特殊字母 以上包含几项
	 */
	private Integer requireChooseItem;
	/**
	 * 首次登陆是否需要修改密码
	 */
	private boolean firstLoginUpdatePassword;

	/**
	 * 密码过期时间（天）
	 * 要求用户在指定的时间间隔内更改其密码。
	 */
	private Integer expires;

	/**
	 * 密码过期提前提醒（天）
	 */
	private Integer expiresWarning;
	/**
	 * 密码连续错误次数
	 */
	private Integer maxFail;

	/**
	 * 密码历史记录
	 */
	private Integer history;
}


class RequireCharset {

}
