package com.xyy.athena.service.id.sequence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * <p><b></b></p>
 *
 * @author XYY
 * @since 2016年4月18日
 * @since JDK 1.8
 *
 */
public class SequenceTableAwareProcessor implements BeanPostProcessor {
	protected Log log = LogFactory.getLog(super.getClass());
	private SequenceTable table;
	
	public void setTable(SequenceTable table) {
		this.table = table;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof SequenceTableAware) {
			if (table == null) {
				log.error(beanName + "'s SequenceTable is null, please set it correctly");
			}
			log.error("Invoking setTable on SequenceTableAware bean '" + beanName + "'");
			((SequenceTableAware)bean).setTable(table);
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

}
