package com.xiongyayun.athena.components.common.exception;

import com.xiongyayun.athena.components.common.i18n.I18nService;
import com.xiongyayun.athena.components.util.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static java.util.stream.Collectors.joining;

/**
 * AthenaInnerException
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2020/8/12
 */
public interface AthenaInnerException extends Serializable {
	Logger log = LoggerFactory.getLogger(AthenaInnerException.class);
	ConcurrentMap<String, String> EXCEPTION_CACHE = new ConcurrentHashMap<>(8);

	/**
	 * 参数
	 * @return	所有参数
	 */
	Object[] getArgs();

	/**
	 * 消息
	 * @return	消息
	 */
	String getMessage();

	/**
	 * 本地化后的信息(翻译后的信息)
	 * @return	本地化后的信息(翻译后的信息)
	 */
	default String getLocalizedMessage() {
		String key = getMessage() + "-" + Arrays.stream(this.getArgs()).map(Object::toString).collect(joining("_"));
		String message;
		if ((message = EXCEPTION_CACHE.get(key)) != null) {
			return message;
		}
		try {
			I18nService I18N_SERVICE = SpringContextUtil.getBean(I18nService.class);
			message = I18N_SERVICE.get(getMessage(), this.getArgs(), getMessage());
			EXCEPTION_CACHE.put(key, message);
			return message;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return getMessage();
	}
}
