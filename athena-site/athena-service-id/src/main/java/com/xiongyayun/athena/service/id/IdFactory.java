package com.xiongyayun.athena.service.id;

import com.xiongyayun.athena.service.Service;

/**
 * ID生成工厂服务
 *
 * @author Yayun.Xiong
 * @date 2019-04-14 16:43
 */
public interface IdFactory extends Service {
	/**
	 * 生成单个ID
	 * @return
	 */
	Object generate();

	/**
	 * 生成指定数量的ID
	 * @param number		数量
	 * @return
	 */
	Object[] generate(int number);
}
