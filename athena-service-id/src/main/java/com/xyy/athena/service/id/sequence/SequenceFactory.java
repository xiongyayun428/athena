package com.xyy.athena.service.id.sequence;

import com.xyy.athena.service.id.IdFactory;

/**
 * 序号工厂
 *
 * @author Yayun.Xiong
 * @date 2019-04-14 17:51
 */
public interface SequenceFactory extends IdFactory {
	/**
	 * 获取类型
	 * @return
	 */
	String getType();

	/**
	 * 获取格式化
	 * @return
	 */
	SequenceFormat getFormat();
}
