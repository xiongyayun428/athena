package com.xiongyayun.athena.core.i18n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
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
    private final MessageSource messageSource;

    @Autowired
	public I18nService(MessageSource messageSource) {
		if (messageSource instanceof ResourceBundleMessageSource) {
			ResourceBundleMessageSource rbms = (ResourceBundleMessageSource) messageSource;
			rbms.addBasenames("i18n/error");
			this.messageSource = rbms;
		} else {
			ResourceBundleMessageSource source = new ResourceBundleMessageSource();
			source.setParentMessageSource(messageSource);
			source.setBasenames("i18n/error");
			source.setUseCodeAsDefaultMessage(true);
			source.setDefaultEncoding("UTF-8");
			this.messageSource = source;
		}
	}

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
