package com.xiongyayun.athena.spring.boot.actuator.autoconfigure.disk;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 磁盘空间使用情况
 *
 * @author Yayun.Xiong
 * @date 2021/9/20
 */
@ConfigurationProperties("management.endpoint.diskspace")
public class DiskSpaceEndPointProperties {
	/** 磁盘已用容量 */
	private String total;
	/** 磁盘剩余容量 */
	private String free;
	/** 磁盘使用率 */
	private String usage;

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getFree() {
		return free;
	}

	public void setFree(String free) {
		this.free = free;
	}

	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}
}
