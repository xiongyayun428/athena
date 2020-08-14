package com.xiongyayun.athena.core.exception;

import com.xiongyayun.athena.core.i18n.I18nService;
import com.xiongyayun.athena.core.utils.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static java.util.stream.Collectors.joining;

/**
 * AthenaInnerException
 *
 * @author: <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date: 2020/8/12
 */
public interface AthenaInnerException {
	Logger log = LoggerFactory.getLogger(AthenaInnerException.class);
	ConcurrentMap<String, String> EXCEPTION_CACHE = new ConcurrentHashMap(8);
	I18nService I18N_SERVICE = SpringContextUtil.getBean(I18nService.class);

	/**
	 * 参数
	 * @return
	 */
	Object[] getArgs();

	/**
	 * 消息
	 * @return
	 */
	String getMessage();

	/**
	 * 本地化后的信息(翻译后的信息)
	 * @return
	 */
	default String getLocalizedMessage() {
		String key = getMessage() + "-" + Arrays.stream(this.getArgs()).map(Object::toString).collect(joining("_"));
		String message;
		if (EXCEPTION_CACHE != null && (message = EXCEPTION_CACHE.get(key)) != null) {
			return message;
		}
		try {
			if (I18N_SERVICE != null) {
				message = I18N_SERVICE.get(getMessage(), this.getArgs(), getMessage());
				EXCEPTION_CACHE.put(key, message);
				return message;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return getMessage();
	}
}
