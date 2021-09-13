package com.xiongyayun.athena.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * IpUtil
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/08/28
 */
public class IpUtil {
	private static final int IP_LENGTH = 4;

	/**
	 * IP地址转成long类型的数字
	 * <p>
	 *     通过左移位操作
	 * </p>
	 *
	 * @param ip	127.0.0.1形式的IP地址
	 * @return {@link Long} Long类型的数字
	 */
	public static Long ipToLong(String ip) {
		if (StringUtil.isBlank(ip)) {
			return null;
		}
		String[] ips = ip.split("\\.");
		if (ips.length != IP_LENGTH) {
			return null;
		}
		return (Long.parseLong(ips[0]) << 24) + (Long.parseLong(ips[1]) << 16) + (Long.parseLong(ips[2]) << 8) + Long.parseLong(ips[3]);
	}

	/**
	 * 将十进制整数形式转换成127.0.0.1形式的IP地址
	 * @param ip	十进制整数形式IP
	 * @return {@link String} 127.0.0.1形式的IP地址
	 */
	public static String longToIp(Long ip) {
		if (ip < 0) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(ip >>> 24).append(".");
		sb.append((ip & 0x00FFFFFF) >>> 16).append(".");
		sb.append((ip & 0x0000FFFF) >>> 8).append(".");
		sb.append(ip & 0x000000FF);
		return sb.toString();
	}

	public static String getIp() throws UnknownHostException {
		return InetAddress.getLocalHost().getHostAddress();
	}

	public static String getLocalMac() throws UnknownHostException, SocketException {
		return getLocalMac(InetAddress.getLocalHost());
	}

	public static String getLocalMac(InetAddress ia) throws SocketException {
		if (ia == null) {
			return null;
		}
		byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < mac.length; i++) {
			if (i != 0) {
				sb.append("-");
			}
			int temp = mac[i] & 0xFF;
			String str = Integer.toHexString(temp);
			if (str.length() == 1) {
				sb.append("0").append(str);
			} else {
				sb.append(str);
			}
		}
		return sb.toString();
	}

	public static void main(String[] args) throws Exception {
		System.out.println(getIp());
		System.out.println(getLocalMac());
	}
}
