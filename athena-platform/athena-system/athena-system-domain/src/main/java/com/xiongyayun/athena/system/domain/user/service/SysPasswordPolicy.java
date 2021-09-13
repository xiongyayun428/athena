package com.xiongyayun.athena.system.domain.user.service;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiongyayun.athena.core.exception.AthenaRuntimeException;
import com.xiongyayun.athena.core.entity.Entity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * SysPasswordPolicy
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/3/30
 */
@Data
@Accessors(chain = true)
@TableName("sys_password_policy")
public class SysPasswordPolicy implements Entity {
	@TableId(value = "policy_type", type = IdType.INPUT)
	private String policyType;
	/**
	 * 密码策略disable状态（true：禁用，1：禁用；false: 启用，0: 启用）
	 */
	private Boolean disabled;
	/**
	 * 密码最小长度
	 */
	private Integer minLength;
	/**
	 * 密码最大长度
	 */
	private Integer maxLength;
	/**
	 * 密码中至少要包含多少个数字
	 */
	private Integer minPasswordDigits;
	/**
	 * 密码中至少要包含多少个小写字母
	 */
	private Integer minPasswordLower;
	/**
	 * 密码中至少要包含多少个大写字母
	 */
	private Integer minPasswordUpper;
	/**
	 * 密码中至少要包含多少个特殊字符
	 */
	private Integer minPasswordSpecialCharacters;
	/**
	 * 密码中至少要包含以上四项中的几项
	 */
	private Integer minPasswordChooseItem;
	/**
	 * 最大连续相同字符数
	 */
	private Integer maxConsecutiveSameCharactersNum;
	/**
	 * 密码轮换限制：密码更换不能使用最近使用过的密码
	 */
	private Integer reuseLimit;
	/**
	 * 密码到期时间天数
	 */
	private Integer expires;
	/**
	 * 密码到期提前提醒（天）
	 */
	private Integer expiresWarning;
	/**
	 * 密码错误最大重试次数
	 */
	private Integer maxRetryCount;
	/**
	 * 密码错误最大重试次数间隔(分钟)
	 */
	private Integer maxRetryInterval;
	/**
	 * 密码连续错误后锁定时长
	 */
	private Integer lockDuration;
	/**
	 * 首次登陆是否需要修改密码（true：需要，1：需要；false: 不需要，0: 不需要）
	 */
	private Boolean firstLoginUpdatePassword;

	public boolean valid(String password) throws AthenaRuntimeException {
		String pwd = password != null ? password.trim() : null;
		if (this.getDisabled()) {
			return true;
		}
		if (getMinLength() != null && pwd.length() < getMinLength()) {
			throw new AthenaRuntimeException("密码最小长度: " + getMinLength());
		}
		if (getMaxLength() != null && pwd.length() > getMaxLength()) {
			throw new AthenaRuntimeException("密码最大长度: " + getMaxLength());
		}

		if (getMinPasswordChooseItem() != null) {
			char[] pwdOfArray = pwd.toCharArray();
			int passwordDigits = 0, passwordLower = 0, passwordUpper = 0, passwordSpecialCharacters = 0;
			for (char c : pwdOfArray) {
				if (c >= '0' && c <= '9') {
					passwordDigits++;
				} else if (c >= 'a' && c <= 'z') {
					passwordLower++;
				} else if (c >= 'A' && c <= 'Z') {
					passwordUpper++;
				} else if (isChinese(c)) {
					// 中文
				} else {
					passwordSpecialCharacters++;
				}
			}
			List<Boolean> choose = new ArrayList();
			if (getMinPasswordDigits() == null || passwordDigits >= getMinPasswordDigits()) {
				choose.add(true);
			}
			if (getMinPasswordLower() == null || passwordLower >= getMinPasswordLower()) {
				choose.add(true);
			}
			if (getMinPasswordUpper() == null || passwordUpper >= getMinPasswordUpper()) {
				choose.add(true);
			}
			if (getMinPasswordSpecialCharacters() == null || passwordSpecialCharacters >= getMinPasswordSpecialCharacters()) {
				choose.add(true);
			}
			if (choose.stream().filter(item -> item).count() < getMinPasswordChooseItem()) {
				throw new AthenaRuntimeException("密码至少要包含以下" + getMinPasswordChooseItem() + "项【" +
						getMinPasswordDigits() + "个数字、" +
						getMinPasswordLower() + "个小写字母、" +
						getMinPasswordUpper() + "个大写字母、" +
						getMinPasswordSpecialCharacters() + "个特殊字符】");
			}
		}

		if (getMaxConsecutiveSameCharactersNum() != null) {
			List<String> list = new ArrayList();
			// 查找相同的子串
			int start = 0;
			for (int i = 0; i < pwd.length() - 1; i++) {
				if (pwd.charAt(i) == pwd.charAt(i + 1)) {
					// 末尾时，直接加上
					if (i + 2 == pwd.length()) {
						list.add(pwd.substring(start));
					}
					continue;
				} else {
					list.add(pwd.substring(start, i + 1));
					start = i + 1;
					continue;
				}
			}
			if (list.stream().anyMatch(item -> item.length() > getMaxConsecutiveSameCharactersNum())) {
				throw new AthenaRuntimeException("密码存在" + getMaxConsecutiveSameCharactersNum() + "个连续相同的字符");
			}
		}

		return true;
	}

	/***
	 * 判断字符是否为中文
	 * @param ch 需要判断的字符
	 * @return 中文返回true，非中文返回false
	 */
	private boolean isChinese(char ch) {
		//获取此字符的UniCodeBlock
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(ch);
		// GENERAL_PUNCTUATION 判断中文的“号
		// CJK_SYMBOLS_AND_PUNCTUATION 判断中文的。号
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
			return true;
		}
		return false;
	}
}
