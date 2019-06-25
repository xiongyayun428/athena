package com.xiongyayun.athena.core.annotation.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 枚举验证注解
 *
 * @author: 熊亚运
 * @date: 2019-06-25
 */
@Documented
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Enum.List.class)
@Constraint(validatedBy = {EnumValidator.class})
public @interface Enum {
	String message() default "{*.validation.constraint.Enum.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	/**
	 * the enum's class-type
	 *
	 * @return Class
	 */
	Class<?> clazz();

	/**
	 * the method's name ,which used to validate the enum's value
	 *
	 * @return method's name
	 */
	String method() default "ordinal";

	/**
	 * Defines several {@link Enum} annotations on the same element.
	 *
	 * @see Enum
	 */
	@Documented
	@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
	@Retention(RetentionPolicy.RUNTIME)
	@interface List {
		Enum[] value();
	}
}
