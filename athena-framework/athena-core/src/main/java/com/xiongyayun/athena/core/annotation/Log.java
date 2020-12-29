package com.xiongyayun.athena.core.annotation;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * Logger
 *
 * @author Yayun.Xiong
 * @date 2019-05-26
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
//@ApiOperation("") // TODO 每次取值都是从这里拿的，别名没有生效
public @interface Log {
    /**
     *
     * @return	说明
     */
//	@AliasFor(annotation = ApiOperation.class, attribute = "value")
    String value();

    /**
     * 启用
     * @return	是否启用？
     */
    boolean enabled() default true;

    /**
     * 保存
     * @return	是否保存
     */
    boolean save() default false;
}
