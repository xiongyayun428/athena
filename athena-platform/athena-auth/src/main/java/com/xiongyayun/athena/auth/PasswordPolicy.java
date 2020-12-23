package com.xiongyayun.athena.auth;

/**
 * 密码策略
 *
 * @author Yayun.Xiong
 * @date 2020/5/11
 */
public class PasswordPolicy {
	/**
	 * disable 状态 （true：禁用, false: 启用）
	 * enabled
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
	 * 至少要包含多少个数字
	 */
	private Integer minPasswordDigits;
	/**
	 * 至少要包含多少个小写字母
	 */
	private Integer minPasswordLower;
	/**
	 * 至少要包含多少个大写字母
	 */
	private Integer minPasswordUpper;
	/**
	 * 至少要包含多少个特殊字符(除空格)
	 */
	private Integer minPasswordSpecialCharacters;
	/**
	 * 至少要包含以上四项中的几项
	 */
	private Integer minPasswordChooseItem;
	/**
	 * 最大连续相同字符数
	 */
	private Integer maxConsecutiveSameCharactersNum;
	/**
	 * 首次登陆是否需要修改密码
	 */
	private boolean firstLoginUpdatePassword;

	/**
	 * 密码到期天数（天）
	 * 要求用户在指定的时间间隔内更改其密码。
	 */
	private Integer expires;

	/**
	 * 密码到期提前提醒（天）
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
