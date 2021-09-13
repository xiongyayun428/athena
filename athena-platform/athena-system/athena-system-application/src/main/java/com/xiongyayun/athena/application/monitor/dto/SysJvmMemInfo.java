package com.xiongyayun.athena.application.monitor.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JVM内存信息
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/4/23
 */
@NoArgsConstructor
@Data
public class SysJvmMemInfo {
	/**
	 * 最大内存
	 */
	private String jvmMaxMemory;

	/**
	 * 可用内存
	 */
	private String jvmUsableMemory;

	/**
	 * 总内存
	 */
	private String jvmTotalMemory;

	/**
	 * 已使用内存
	 */
	private String jvmUsedMemory;

	/**
	 * 空余内存
	 */
	private String jvmFreeMemory;

	/**
	 * 使用率
	 */
	private String jvmMemoryUsedRate;
}
