package com.xiongyayun.athena.core.i18n;

import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

/**
 * AthenaMessageResource
 *
 * @author: <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date: 2020/8/11
 */
public class AthenaMessageResource extends ResourceBundleMessageSource implements ResourceLoaderAware {
	private ResourceLoader resourceLoader;

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = (resourceLoader == null ? new DefaultResourceLoader() : resourceLoader);
	}
}
