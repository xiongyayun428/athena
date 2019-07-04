package com.xiongyayun.athena.core.i18n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * I18nService
 *
 * @author: Yayun.Xiong
 * @date 2019-05-26
 */
@Component
public class I18nService {

    @Autowired
    private MessageSource messageSource;

    public String get(String code) {
        return get(code, null);
    }

    public String get(String code, @Nullable Object[] args) {
        return get(code, args, "");
    }

    public String get(String code, @Nullable Object[] args, @Nullable String defaultMsg) {
        //这里使用比较方便的方法，不依赖request.
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, args, defaultMsg, locale);
    }
}
