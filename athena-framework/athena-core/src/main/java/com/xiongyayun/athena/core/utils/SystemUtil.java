package com.xiongyayun.athena.core.utils;

import cn.hutool.core.util.StrUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.log.LogFormatUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SystemUtil {
	private final static Log log = LogFactory.getLog(SystemUtil.class);
	private static Set<String> localAddress;

	/**
	 * 获取来访者的浏览器版本
	 *
	 * @param request
	 * @return
	 */
	public static String getRequestBrowserInfo(HttpServletRequest request) {
		String browserVersion = null;
		String header = request.getHeader("user-agent");
		if (header == null || "".equals(header)) {
			return "";
		}
		if (header.indexOf("MSIE") > 0) {
			browserVersion = "IE";
		} else if (header.indexOf("Firefox") > 0) {
			browserVersion = "Firefox";
		} else if (header.indexOf("Chrome") > 0) {
			browserVersion = "Chrome";
		} else if (header.indexOf("Safari") > 0) {
			browserVersion = "Safari";
		} else if (header.indexOf("Camino") > 0) {
			browserVersion = "Camino";
		} else if (header.indexOf("Konqueror") > 0) {
			browserVersion = "Konqueror";
		}
		return browserVersion;
	}

	/**
	 * 获取系统版本信息
	 *
	 * @param request
	 * @return
	 */
	public static String getRequestSystemInfo(HttpServletRequest request) {
		String systenInfo = null;
		String header = request.getHeader("user-agent");
		if (header == null || "".equals(header)) {
			return "";
		}
		// 得到用户的操作系统
		if (header.indexOf("NT 6.0") > 0) {
			systenInfo = "Windows Vista/Server 2008";
		} else if (header.indexOf("NT 5.2") > 0) {
			systenInfo = "Windows Server 2003";
		} else if (header.indexOf("NT 5.1") > 0) {
			systenInfo = "Windows XP";
		} else if (header.indexOf("NT 6.0") > 0) {
			systenInfo = "Windows Vista";
		} else if (header.indexOf("NT 6.1") > 0) {
			systenInfo = "Windows 7";
		} else if (header.indexOf("NT 6.2") > 0) {
			systenInfo = "Windows Slate";
		} else if (header.indexOf("NT 6.3") > 0) {
			systenInfo = "Windows 9";
		} else if (header.indexOf("NT 5") > 0) {
			systenInfo = "Windows 2000";
		} else if (header.indexOf("NT 4") > 0) {
			systenInfo = "Windows NT4";
		} else if (header.indexOf("Me") > 0) {
			systenInfo = "Windows Me";
		} else if (header.indexOf("98") > 0) {
			systenInfo = "Windows 98";
		} else if (header.indexOf("95") > 0) {
			systenInfo = "Windows 95";
		} else if (header.indexOf("Mac") > 0) {
			systenInfo = "Mac";
		} else if (header.indexOf("Unix") > 0) {
			systenInfo = "UNIX";
		} else if (header.indexOf("Linux") > 0) {
			systenInfo = "Linux";
		} else if (header.indexOf("SunOS") > 0) {
			systenInfo = "SunOS";
		}
		return systenInfo;
	}

	/**
	 * 获取客户端IP
	 *
	 * @return
	 */
	public static String getClientIP(HttpServletRequest request) {
		if (request == null) {
			return null;
		}
		String s = request.getHeader("X-Forwarded-For");
		if (s == null || s.length() == 0 || "unknown".equalsIgnoreCase(s)) {
			s = request.getHeader("Proxy-Client-IP");
		}
		if (s == null || s.length() == 0 || "unknown".equalsIgnoreCase(s)) {
			s = request.getHeader("WL-Proxy-Client-IP");
		}
		if (s == null || s.length() == 0 || "unknown".equalsIgnoreCase(s)) {
			s = request.getHeader("HTTP_CLIENT_IP");
		}
		if (s == null || s.length() == 0 || "unknown".equalsIgnoreCase(s)) {
			s = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (s == null || s.length() == 0 || "unknown".equalsIgnoreCase(s)) {
			s = request.getRemoteAddr();
		}
		if ("127.0.0.1".equals(s) || "0:0:0:0:0:0:0:1".equals(s)) {
			try {
				s = InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e) {
				log.error(e.getMessage(), e);
			}
		}
		return s;
	}

	/**
	 *
	 * @param ip
	 * @return
	 */
	public static String getClientMac(String ip) {
		if (getLocalAddress().contains(ip)) {
			return getLocalMac();
		}
		String macAddress;
		macAddress = getMacInWindows(ip).trim();
		if (StrUtil.isBlank(macAddress)) {
			macAddress = getMacInLinux(ip).trim();
		}
		return macAddress;
	}

	/**
	 * 获取客户端MAC
	 * @param request
	 * @return
	 */
	public static String getClientMac(HttpServletRequest request) {
		String ip = getClientIP(request);
		if (getLocalAddress().contains(ip)) {
			return getLocalMac();
		}
		if (getRequestSystemInfo(request).startsWith("Windows")) {
			return getMacInWindows(ip).trim();
		} else {
			return getMacInLinux(ip).trim();
		}
	}

	/**
	 * 执行命令
	 * @param cmd
	 * @return
	 */
	private static String callCmd(String[] cmd) {
		StringBuilder result = new StringBuilder();
		String line;
		try {
			Process proc = Runtime.getRuntime().exec(cmd);
			InputStreamReader is = new InputStreamReader(proc.getInputStream(), Charset.forName("GBK"));
			BufferedReader br = new BufferedReader(is);
			while ((line = br.readLine()) != null) {
				result.append(line).append("\r\n");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return result.toString();
	}

	/**
	 * 执行命令
	 * @param cmd		第一个命令
	 * @param another	第二个命令
	 * @return			第二个命令的执行结果
	 */
	private static String callCmd(String[] cmd, String[] another) {
		StringBuilder result = new StringBuilder();
		String line;
		try {
			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec(cmd);
			proc.waitFor(); // 已经执行完第一个命令，准备执行第二个命令
			proc = rt.exec(another);
			InputStreamReader is = new InputStreamReader(proc.getInputStream(), Charset.forName("GBK"));
			BufferedReader br = new BufferedReader(is);
			while ((line = br.readLine()) != null) {
				result.append(line).append("\r\n");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return result.toString();
	}

	/**
	 * 过滤Mac地址
	 * @param ip				目标ip,一般在局域网内
	 * @param sourceString		命令处理的结果字符串
	 * @param macSeparator		mac分隔符号
	 * @return					mac地址，用上面的分隔符号表示
	 */
	private static String filterMacAddress(String ip, String sourceString, String macSeparator) {
		String result = "";
		String regExp = "((([0-9,A-Fa-f]{1,2}" + macSeparator + "){1,5})[0-9,A-Fa-f]{1,2})";
		Pattern pattern = Pattern.compile(regExp);
		Matcher matcher = pattern.matcher(sourceString);
		int index = sourceString.indexOf(ip);
		if (index > 0) {
			while (matcher.find()) {
				result = matcher.group(1);
				if (index <= sourceString.indexOf(result)) {
					break; // 如果有多个IP,只匹配本IP对应的Mac.
				} else {
					result = "";
				}
			}
		}
		return result;
	}

	private static Set<String> getLocalAddress() {
		if (localAddress == null) {
			try {
				localAddress = new HashSet<>();
				synchronized (SystemUtil.class) {
					if (localAddress == null) {
						// 获取本地IP对象
						InetAddress ia = InetAddress.getLocalHost();
						localAddress.add("127.0.0.1");
						localAddress.add("0:0:0:0:0:0:0:1");
						localAddress.add("localhost");
						localAddress.add(ia.getHostAddress());
						localAddress.add(ia.getHostName());
					}
				}
			} catch (UnknownHostException e) {
				log.error(e.getMessage(), e);
			}
		}
		return localAddress;
	}

	/**
	 * 获取本机Mac地址
	 * @return
	 */
	private static String getLocalMac() {
		try {
			// 获取本地IP对象
			InetAddress ia = InetAddress.getLocalHost();
			// 获得网络接口对象（即网卡），并得到mac地址，mac地址存在于一个byte数组中。
			byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
			StringBuilder sb = new StringBuilder();
	        for (int i = 0; i < mac.length; i++) {
	            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
	        }
	        return sb.toString();
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 获取局域网内Mac地址
	 * @param ip		目标ip
	 * @return
	 */
	private static String getMacInWindows(final String ip) {
		String result;
		String[] cmd = { "cmd", "/c", "ping " + ip };
		String[] another = { "cmd", "/c", "arp -a" };
		String cmdResult = callCmd(cmd, another);
		result = filterMacAddress(ip, cmdResult, "-");
		return result;
	}

	/**
	 * 获取局域网内Mac地址
	 * @param ip		目标ip
	 * @return
	 */
	private static String getMacInLinux(final String ip) {
		String result;
		String[] cmd = { "/bin/sh", "-c", "ping " + ip + " -c 2 && arp -a" };
		String cmdResult = callCmd(cmd);
		result = filterMacAddress(ip, cmdResult, ":");
		return result;
	}

	public static void logRequest(HttpServletRequest request) {
		LogFormatUtils.traceDebug(log, traceOn -> {
			String params;
//            if (isEnableLoggingRequestDetails()) {
			params = request.getParameterMap().entrySet().stream()
					.map(entry -> entry.getKey() + ":" + Arrays.toString(entry.getValue()))
					.collect(Collectors.joining(", "));
//            }
//            else {
//                params = (request.getParameterMap().isEmpty() ? "" : "masked");
//            }

			String queryString = request.getQueryString();
			String queryClause = (StringUtils.hasLength(queryString) ? "?" + queryString : "");
			String dispatchType = (!request.getDispatcherType().equals(DispatcherType.REQUEST) ?
					"\"" + request.getDispatcherType().name() + "\" dispatch for " : "");
			String message = (dispatchType + request.getMethod() + " \"" + getRequestUri(request) +
					queryClause + "\", parameters={" + params + "}");

			if (traceOn) {
				List<String> values = Collections.list(request.getHeaderNames());
				String headers;
//                if (isEnableLoggingRequestDetails()) {
				headers = values.stream().map(name -> name + ":" + Collections.list(request.getHeaders(name)))
						.collect(Collectors.joining(", "));
//                }
				return message + ", headers={" + headers + "}"; // in DispatcherServlet '" + getServletName() + "'";
			}
			else {
				return message;
			}
		});
	}

	public static String getRequestUri(HttpServletRequest request) {
		String uri = (String) request.getAttribute(WebUtils.INCLUDE_REQUEST_URI_ATTRIBUTE);
		if (uri == null) {
			uri = request.getRequestURI();
		}
		return uri;
	}

	public static void main(String[] args) {
		System.out.println(SystemUtil.getClientMac("127.0.0.1"));
		System.out.println(SystemUtil.getClientMac("0:0:0:0:0:0:0:1"));
		System.out.println(SystemUtil.getClientMac("localhost"));
	}

}
