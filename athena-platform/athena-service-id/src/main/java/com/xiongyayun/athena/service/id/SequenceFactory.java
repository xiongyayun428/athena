package com.xiongyayun.athena.service.id;

import com.xiongyayun.athena.service.id.sequence.SequenceFormat;

/**
 * 序号工厂
 *
 * @author: Yayun.Xiong
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
