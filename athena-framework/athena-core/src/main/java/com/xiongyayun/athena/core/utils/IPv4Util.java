package com.xiongyayun.athena.core.utils;

import java.net.Inet4Address;
import java.net.InetAddress;

/**
 * IPv4Util
 *
 * @author Yayun.Xiong
 * @date 2019/11/15
 */
public class IPv4Util {
	private final static int INADDRSZ = 4;

	/**
	 * 把IP地址转化为字节数组
	 *
	 * @param host
	 * @return byte[]
	 */
	public static byte[] ipToBytesByInet(String host) {
		try {
			return InetAddress.getByName(host).getAddress();
		} catch (Exception e) {
			throw new IllegalArgumentException(host + " is invalid IP");
		}
	}

	/**
	 * 把IP地址转化为int
	 * @param ip
	 * @return ip的int地址
	 */
	public static byte[] ipToBytesByReg(String ip) {
		byte[] ret = new byte[4];
		try {
			String[] ipArr = ip.split("\\.");
			ret[0] = (byte) (Integer.parseInt(ipArr[0]) & 0xFF);
			ret[1] = (byte) (Integer.parseInt(ipArr[1]) & 0xFF);
			ret[2] = (byte) (Integer.parseInt(ipArr[2]) & 0xFF);
			ret[3] = (byte) (Integer.parseInt(ipArr[3]) & 0xFF);
			return ret;
		} catch (Exception e) {
			throw new IllegalArgumentException(ip + " is invalid IP");
		}

	}

	/**
	 * 字节数组转化为IP
	 *
	 * @param bytes
	 * @return int
	 */
	public static String bytesToIp(byte[] bytes) {
		return new StringBuffer().append(bytes[0] & 0xFF).append('.').append(
				bytes[1] & 0xFF).append('.').append(bytes[2] & 0xFF)
				.append('.').append(bytes[3] & 0xFF).toString();
	}

	/**
	 * 根据位运算把 byte[] -> int
	 *
	 * @param bytes
	 * @return int
	 */
	public static int bytesToInt(byte[] bytes) {
		int addr = bytes[3] & 0xFF;
		addr |= ((bytes[2] << 8) & 0xFF00);
		addr |= ((bytes[1] << 16) & 0xFF0000);
		addr |= ((bytes[0] << 24) & 0xFF000000);
		return addr;
	}

	/**
	 * 把IP地址转化为int
	 *
	 * @param ip
	 * @return int
	 */
	public static int ipToInt(String ip) {
		try {
			return bytesToInt(ipToBytesByInet(ip));
		} catch (Exception e) {
			throw new IllegalArgumentException(ip + " is invalid IP");
		}
	}

	/**
	 * ipInt -> byte[]
	 *
	 * @param ipInt
	 * @return byte[]
	 */
	public static byte[] intToBytes(int ipInt) {
		byte[] byteOfIp = new byte[INADDRSZ];
		byteOfIp[0] = (byte) ((ipInt >>> 24) & 0xFF);
		byteOfIp[1] = (byte) ((ipInt >>> 16) & 0xFF);
		byteOfIp[2] = (byte) ((ipInt >>> 8) & 0xFF);
		byteOfIp[3] = (byte) (ipInt & 0xFF);
		return byteOfIp;
	}

	/**
	 * 把int->ip地址
	 *
	 * @param ipInt
	 * @return String
	 */
	public static String intToIp(int ipInt) {
		return new StringBuilder().append(((ipInt >> 24) & 0xff)).append('.')
				.append((ipInt >> 16) & 0xff).append('.').append(
						(ipInt >> 8) & 0xff).append('.').append((ipInt & 0xff))
				.toString();
	}

	/**
	 * 把192.168.1.1/24 转化为int数组范围
	 *
	 * @param ipAndMask
	 * @return int[]
	 */
	public static int[] getIPIntScope(String ipAndMask) {
		String[] ipArr = ipAndMask.split("/");
		if (ipArr.length != 2) {
			throw new IllegalArgumentException("invalid ipAndMask with: " + ipAndMask);
		}
		int netMask = Integer.valueOf(ipArr[1].trim());
		if (netMask < 0 || netMask > 31) {
			throw new IllegalArgumentException("invalid ipAndMask with: " + ipAndMask);
		}
		int ipInt = IPv4Util.ipToInt(ipArr[0]);
		int netIP = ipInt & (0xFFFFFFFF << (32 - netMask));
		int hostScope = (0xFFFFFFFF >>> netMask);
		return new int[]{netIP, netIP + hostScope};

	}

	/**
	 * 把192.168.1.1/24 转化为IP数组范围
	 *
	 * @param ipAndMask
	 * @return String[]
	 */
	public static String[] getIPAddrScope(String ipAndMask) {
		int[] ipIntArr = IPv4Util.getIPIntScope(ipAndMask);
		return new String[]{IPv4Util.intToIp(ipIntArr[0]), IPv4Util.intToIp(ipIntArr[0])};
	}

	/**
	 * 根据IP 子网掩码（192.168.1.1 255.255.255.0）转化为IP段
	 *
	 * @param ipAddr ipAddr
	 * @param mask   mask
	 * @return int[]
	 */
	public static int[] getIPIntScope(String ipAddr, String mask) {

		int ipInt;
		int netMaskInt, ipcount;
		try {
			ipInt = IPv4Util.ipToInt(ipAddr);
			if (null == mask || "".equals(mask)) {
				return new int[]{ipInt, ipInt};
			}
			netMaskInt = IPv4Util.ipToInt(mask);
			ipcount = IPv4Util.ipToInt("255.255.255.255") - netMaskInt;
			int netIP = ipInt & netMaskInt;
			int hostScope = netIP + ipcount;
			return new int[]{netIP, hostScope};
		} catch (Exception e) {
			throw new IllegalArgumentException("invalid ip scope express  ip:" + ipAddr + "  mask:" + mask);
		}

	}

	/**
	 * 根据IP 子网掩码（192.168.1.1 255.255.255.0）转化为IP段
	 *
	 * @param ipAddr ipAddr
	 * @param mask   mask
	 * @return String[]
	 */
	public static String[] getIPStrScope(String ipAddr, String mask) {
		int[] ipIntArr = IPv4Util.getIPIntScope(ipAddr, mask);
		return new String[]{IPv4Util.intToIp(ipIntArr[0]), IPv4Util.intToIp(ipIntArr[0])};
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String ipAddr = "192.168.8.1";

		byte[] bytearr = IPv4Util.ipToBytesByInet(ipAddr);

		StringBuffer byteStr = new StringBuffer();

		for (byte b : bytearr) {
			if (byteStr.length() == 0) {
				byteStr.append(b);
			} else {
				byteStr.append("," + b);
			}
		}
		System.out.println("IP: " + ipAddr + " ByInet --> byte[]: [ " + byteStr + " ]");

		bytearr = IPv4Util.ipToBytesByReg(ipAddr);
		byteStr = new StringBuffer();

		for (byte b : bytearr) {
			if (byteStr.length() == 0) {
				byteStr.append(b);
			} else {
				byteStr.append("," + b);
			}
		}
		System.out.println("IP: " + ipAddr + " ByReg  --> byte[]: [ " + byteStr + " ]");

		System.out.println("byte[]: " + byteStr + " --> IP: " + IPv4Util.bytesToIp(bytearr));

		int ipInt = IPv4Util.ipToInt(ipAddr);

		System.out.println("IP: " + ipAddr + "  --> int: " + ipInt);

		System.out.println("int: " + ipInt + " --> IP: " + IPv4Util.intToIp(ipInt));

		String ipAndMask = "192.168.1.1/24";

		int[] ipscope = IPv4Util.getIPIntScope(ipAndMask);
		System.out.println(ipAndMask + " --> int地址段：[ " + ipscope[0] + "," + ipscope[1] + " ]");

		System.out.println(ipAndMask + " --> IP 地址段：[ " + IPv4Util.intToIp(ipscope[0]) + "," + IPv4Util.intToIp(ipscope[1]) + " ]");

		String ipAddr1 = "192.168.1.1", ipMask1 = "255.255.255.0";

		int[] ipscope1 = IPv4Util.getIPIntScope(ipAddr1, ipMask1);
		System.out.println(ipAddr1 + " , " + ipMask1 + "  --> int地址段 ：[ " + ipscope1[0] + "," + ipscope1[1] + " ]");

		System.out.println(ipAddr1 + " , " + ipMask1 + "  --> IP地址段 ：[ " + IPv4Util.intToIp(ipscope1[0]) + "," + IPv4Util.intToIp(ipscope1[1]) + " ]");

		System.out.println(inet_aton(ipAddr));
		System.out.println(inet_ntoa(3232237569L));
	}


	public static String inet_ntoa(long add) {
		return ((add & 0xff000000) >> 24) + "." + ((add & 0xff0000) >> 16) + "." + ((add & 0xff00) >> 8) + "." + ((add & 0xff));
	}

	public static long inet_aton(Inet4Address add) {
		byte[] bytes = add.getAddress();
		long result = 0;
		for (byte b : bytes) {
			if ((b & 0x80L) != 0) {
				result += 256L + b;
			} else {
				result += b;
			}
			result <<= 8;
		}
		result >>= 8;
		return result;
	}

	/**
	 * significantly faster than parse the string into long
	 */
	public static long inet_aton(String add) {
		long result = 0;
		// number between a dot
		long section = 0;
		// which digit in a number
		int times = 1;
		// which section
		int dots = 0;
		for (int i = add.length() - 1; i >= 0; --i) {
			if (add.charAt(i) == '.') {
				times = 1;
				section <<= dots * 8;
				result += section;
				section = 0;
				++dots;
			} else {
				section += (add.charAt(i) - '0') * times;
				times *= 10;
			}
		}
		section <<= dots * 8;
		result += section;
		return result;
	}

}
