package com.xiongyayun.athena.core.validation.date;

import cn.hutool.core.date.DateUtil;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * DateValidator
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/4/21
 */
public class DateValidator implements ConstraintValidator<Date, String> {
	private String pattern;

	@Override
	public void initialize(Date constraintAnnotation) {
		this.pattern = constraintAnnotation.value();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		// 为空则放过，因为在此校验之前会加入@NotNull或@NotBlank校验
		if (!StringUtils.hasLength(value)) {
			return true;
		}
		//长度不对直接返回
		if (value.length() != this.pattern.length()) {
			return false;
		}
		try {
			DateUtil.parse(value, this.pattern);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
