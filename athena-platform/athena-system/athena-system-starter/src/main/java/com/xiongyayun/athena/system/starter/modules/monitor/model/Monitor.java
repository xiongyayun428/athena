package com.xiongyayun.athena.system.modules.monitor.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Monitor
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/4/23
 */
@NoArgsConstructor
@Data
public class Monitor implements Serializable {
	/**
	 * 系统信息
	 */
	private SysOsInfo sysOsInfo;

	/**
	 * Java信息
	 */
	private SysJavaInfo sysJavaInfo;

	/**
	 * JVM内存信息
	 */
	private SysJvmMemInfo sysJvmMemInfo;
}
