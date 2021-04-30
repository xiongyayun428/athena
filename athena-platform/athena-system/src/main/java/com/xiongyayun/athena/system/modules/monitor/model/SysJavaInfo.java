package com.xiongyayun.athena.system.modules.monitor.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JVM信息
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/4/23
 */
@NoArgsConstructor
@Data
public class SysJavaInfo {
	/**
	 * 虚拟机名称
	 */
	private String jvmName;

	/**
	 * 虚拟机版本
	 */
	private String jvmVersion;

	/**
	 * 虚拟机供应商
	 */
	private String jvmVendor;

	/**
	 * java名称
	 */
	private String javaName;

	/**
	 * java版本
	 */
	private String javaVersion;
}
