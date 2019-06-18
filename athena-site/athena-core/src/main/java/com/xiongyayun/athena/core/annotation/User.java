package com.xiongyayun.athena.core.annotation;

import java.lang.annotation.*;

/**
 * 用户信息
 * @author Yayun.Xiong
 * @date 2019-05-26
 */
@Target({ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface User {}

