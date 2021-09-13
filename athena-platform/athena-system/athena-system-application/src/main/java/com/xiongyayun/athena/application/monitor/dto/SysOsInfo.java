package com.xiongyayun.athena.application.monitor.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统信息
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/4/23
 */
@NoArgsConstructor
@Data
public class SysOsInfo {
	/**
	 * 系统名称
	 */
	private String osName;

	/**
	 * 系统架构
	 */
	private String osArch;

	/**
	 * 系统版本
	 */
	private String osVersion;

	/**
	 * 主机名称
	 */
	private String osHostName;

	/**
	 * 主机ip地址
	 */
	private String osHostAddress;
}
