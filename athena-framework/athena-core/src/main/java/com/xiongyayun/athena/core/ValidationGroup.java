package com.xiongyayun.athena.core;

/**
 * ValidationGroup
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/4/19
 */
public interface ValidationGroup {
	/**
	 * validation分组校验使用，创建的时候需要校验
	 */
	interface Create {}

	/**
	 * validation分组校验使用，更新的时候需要校验
	 */
	interface Update {}
}
