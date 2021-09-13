package com.xiongyayun.athena.core.annotation;

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
public @interface Log {
    /**
     *
     * @return	说明
     */
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
