package com.xiongyayun.athena.core.validation.dict;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 检验值是否为字典值，验证sys_dict表中有没有相关的字典项
 * <p>
 *     本注解用的时候，一定要加dictCode参数，用来表明验证的哪个字典类型中的值
 * </p>
 * dictType值来自数据库中sys_dict表的dict_code值
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/4/20
 */
@Documented
@Constraint(validatedBy = DictValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Dict {
	String message() default "不正确的字典值，请检查数据库中是否录入该字典项";

	Class[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	/**
	 * 字典的类型
	 */
	String[] dictCode() default {};

	String[] value();

	@Target({ElementType.FIELD, ElementType.PARAMETER})
	@Retention(RUNTIME)
	@Documented
	@interface List {
		Dict[] value();
	}
}
