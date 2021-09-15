package com.xiongyayun.athena.components.common.exception;

import com.xiongyayun.athena.components.common.ResBody;
import com.xiongyayun.athena.components.common.i18n.I18nService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.util.Arrays;

/**
 * TranslateException
 *
 * @author Yayun.Xiong
 * @date 2021/9/14
 */
public class TranslateException {
	protected static final Logger log = LoggerFactory.getLogger(TranslateException.class);
	private final I18nService i18nService;

	@Autowired
	public TranslateException(I18nService i18nService) {
		this.i18nService = i18nService;
	}

	public ResBody<?> translate(String key, @Nullable Object[] args, @Nullable String defaultMsg, @Nullable Throwable e) {
		final ResBody<?> resBody = new ResBody<>();
		if (i18nService == null) {
			throw new RuntimeException("请配置i18nService");
		}
		// 翻译后
		String i18nValue = i18nService.get(key, args);
		Object val = null;
		if (StringUtils.hasLength(i18nValue)) {
			String[] separator = {" ", ",", "，", "|", ":", "：", "^", ";", "；", "/"};
			String[] i18nValueArray = i18nValue.split("");
			val = Arrays.stream(i18nValueArray)
					.filter(v -> Arrays.asList(separator).contains(v))
					.findFirst()
					.orElse(null)
			;
		}
		// 翻译后存在错误码和错误消息
		if (val != null) {
			int s = i18nValue.indexOf(String.valueOf(val));
			resBody.withCode(i18nValue.substring(0, s)).withMsg(i18nValue.substring(s + 1));
		} else {
			// 没找到翻译
			if (!StringUtils.hasLength(i18nValue)) {
				log.warn("请配置错误码和错误信息: {}", key);
				if (StringUtils.hasLength(defaultMsg)) {
					resBody.setRtnMsg(defaultMsg);
				} else {
					resBody.setRtnMsg(key);
				}
			} else {
				log.warn("错误码和错误信息配置错误: {}", key);
				resBody.setRtnMsg(i18nValue);
			}
			resBody.setRtnCode("999999");
		}
		if (e != null) {
			log.error(resBody.getRtnMsg(), e);
		} else {
			log.info(resBody.getRtnMsg());
		}
		return resBody;
	}
}
