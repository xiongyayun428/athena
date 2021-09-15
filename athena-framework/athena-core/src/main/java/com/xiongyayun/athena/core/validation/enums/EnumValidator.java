package com.xiongyayun.athena.core.validation.enums;

import com.xiongyayun.athena.components.common.exception.AthenaRuntimeException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;

/**
 * 枚举验证注解
 *
 * @author 熊亚运
 * @date 2019-06-25
 */
public class EnumValidator implements ConstraintValidator<Enum, Object> {
	private Enum annotation;

	@Override
	public void initialize(Enum constraintAnnotation) {
		this.annotation = constraintAnnotation;
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if (value == null) {
			return false;
		}

		Object[] objects = annotation.clazz().getEnumConstants();
		try {
			Method method = annotation.clazz().getMethod(annotation.method());
			for (Object o : objects) {
				if (value.equals(method.invoke(o))) {
					return true;
				}
			}
		} catch (Exception e) {
			throw new AthenaRuntimeException(e.getMessage(), e);
		}
		return false;
	}
}
