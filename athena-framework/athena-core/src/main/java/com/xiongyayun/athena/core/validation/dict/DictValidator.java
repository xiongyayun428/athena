package com.xiongyayun.athena.core.validation.dict;

import org.springframework.util.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * DictValidator
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/4/20
 */
public class DictValidator implements ConstraintValidator<Dict, String> {
	private String[] dictCode;

	@Override
	public void initialize(Dict constraintAnnotation) {
		this.dictCode = ObjectUtils.isEmpty(constraintAnnotation.dictCode()) ? constraintAnnotation.value() : constraintAnnotation.dictCode();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		System.out.println(this.dictCode + ":" + value);
		return false;
	}
}
