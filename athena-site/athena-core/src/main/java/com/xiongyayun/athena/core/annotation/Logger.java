package com.xiongyayun.athena.core.annotation;

import java.lang.annotation.*;

/**
 * Logger
 *
 * @author: Yayun.Xiong
 * @date 2019-05-26
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Logger {
    /**
     *
     * @return
     */
    String[] value() default "";

    /**
     * 启用
     * @return
     */
    boolean enabled() default true;

    /**
     * 保存
     * @return
     */
    boolean save() default false;
}
