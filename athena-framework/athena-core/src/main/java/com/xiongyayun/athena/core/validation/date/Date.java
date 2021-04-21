package com.xiongyayun.athena.core.validation.date;

import cn.hutool.core.date.DatePattern;
import org.springframework.core.annotation.AliasFor;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Date
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/4/21
 */
@Documented
@Constraint(validatedBy = DateValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RUNTIME)
@Repeatable(Date.List.class)
public @interface Date {
	String message() default "日期格式不正确，正确格式应为{value}";

	Class[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String value() default DatePattern.NORM_DATE_PATTERN;

	@Target({ElementType.FIELD, ElementType.PARAMETER})
	@Retention(RUNTIME)
	@Documented
	@interface List {
		Date[] value();
	}
}
