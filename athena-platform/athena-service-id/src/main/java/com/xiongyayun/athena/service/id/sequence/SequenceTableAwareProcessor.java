package com.xiongyayun.athena.service.id.sequence;

import lombok.Setter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * bean 初始化前后进行一些处理工作。
 *
 * @author: Yayun.Xiong
 * @date: 2019-04-15 08:58
 */
@Component
public class SequenceTableAwareProcessor implements BeanPostProcessor {
	protected Log log = LogFactory.getLog(super.getClass());
	@Setter
	private SequenceTable table;
	
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof SequenceTableAware) {
			if (table == null) {
				log.error(beanName + "'s SequenceTable is null, please set it correctly");
			}
			log.info("Invoking setTable on SequenceTableAware bean '" + beanName + "'");
			((SequenceTableAware)bean).setTable(table);
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

}
