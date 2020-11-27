package com.xiongyayun.athena.core.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * RequestJson
 *
 * @author: <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date: 2020/11/27
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestJson {
	@AliasFor("name")
	String value() default "";

	@AliasFor("value")
	String name() default "";

	boolean required() default true;
}
