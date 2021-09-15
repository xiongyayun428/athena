package com.xiongyayun.athena.components.common;

/**
 * AppMessage
 *
 * @author Yayun.Xiong
 * @date 2021/9/13
 */
public class AppMessage {
	private String applicationName;
	private String hostName;
	private String pid;
	private String protocol;
	private String contextPath;
	private String ip;
	private Integer port;
	private String[] profiles;

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String[] getProfiles() {
		return profiles;
	}

	public void setProfiles(String[] profiles) {
		this.profiles = profiles;
	}
}
