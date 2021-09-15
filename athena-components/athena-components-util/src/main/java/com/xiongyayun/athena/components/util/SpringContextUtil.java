package com.xiongyayun.athena.components.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * SpringContextUtil
 *
 * @author Yayun.Xiong
 * @date 2021/9/14
 */
@Component("springContextUtil")
public class SpringContextUtil implements ApplicationContextAware, Serializable {
	private static boolean init = false;
	/**
	 * Spring应用上下文环境
	 */
	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
		init = true;
		SpringContextUtil.applicationContext = applicationContext;
	}

	public static void initApplicationContextIfNotSet(ApplicationContext applicationContext) {
		if (!init) {
			SpringContextUtil.applicationContext = applicationContext;
		}
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static <T> T getBean(String name) throws BeansException {
		return (T) applicationContext.getBean(name);
	}

	public static <T> T getBean(Class<?> clz) throws BeansException {
		return (T) applicationContext.getBean(clz);
	}

	public static String getProperty(String key) {
		return getProperty(key, String.class);
	}
	public static <T> T getProperty(String key, Class<T> targetType) {
		return getProperty(key, targetType, null);
	}
	public static <T> T getProperty(String key, Class<T> targetType, T defaultValue) {
		Environment environment = getBean(Environment.class);
		return environment.getProperty(key, targetType, defaultValue);
	}
}
