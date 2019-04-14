package com.xyy.athena.service.id.support;

import com.xyy.athena.service.id.IdFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * AbstractIdFactory
 *
 * @author Yayun.Xiong
 * @date 2019-04-14 18:01
 */
public abstract class AbstractIdFactory implements IdFactory {
    protected Log log = LogFactory.getLog(this.getClass());

    @Override
    public Object[] generate(int number) {
        Object[] array = new Object[number];
        for (int i = 0; i < number; i++) {
            array[i] = generate();
        }
        return array;
    }
}
