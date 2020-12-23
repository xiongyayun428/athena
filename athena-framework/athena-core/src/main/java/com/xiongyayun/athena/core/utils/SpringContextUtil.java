package com.xiongyayun.athena.core.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * SpringContextUtil
 *
 * @author: 熊亚运
 * @date: 2019-05-31
 */
@Component("springContextUtil")
public class SpringContextUtil implements ApplicationContextAware {
    /**
     * Spring应用上下文环境
     */
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
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
