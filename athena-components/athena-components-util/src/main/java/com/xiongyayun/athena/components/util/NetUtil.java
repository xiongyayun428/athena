package com.xiongyayun.athena.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

public class NetUtil {
	private static final Logger log = LoggerFactory.getLogger(NetUtil.class);
	public static final String LOCALHOST = "127.0.0.1";
	public static final String ANYHOST = "0.0.0.0";
	private static final Pattern IP_PATTERN = Pattern.compile("\\d{1,3}(\\d{1,3}){3,5}$");
	private static InetAddress localAddress;

	@NonNull
	public static String getLocalIpAddress() {
		return Optional.ofNullable(getLocalAddress())
				.map(InetAddress::getHostAddress)
				.orElse(LOCALHOST);
	}

	public static InetAddress getLocalAddress() {
		if (localAddress != null) {
			return localAddress;
		}
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			if (isValidAddress(inetAddress)) {
				localAddress = inetAddress;
				return inetAddress;
			}
			final Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			if (Objects.isNull(interfaces)) {
				return null;
			}
			return getInetAddress(interfaces);
		} catch (SocketException e) {
			log.warn("failed to retriving network ip address");
		} catch (UnknownHostException e) {
			log.warn("failed to retriving ip address");
		}
		return null;
	}

	private static InetAddress getInetAddress(Enumeration<NetworkInterface> interfaceEnumeration) {
		if (Objects.isNull(interfaceEnumeration)) {
			return null;
		}
		while (interfaceEnumeration.hasMoreElements()) {
			NetworkInterface networkInterface = interfaceEnumeration.nextElement();
			InetAddress address = getInetAddress(networkInterface);
			if (isValidAddress(address)) {
				return address;
			}
		}
		return null;
	}

	private static InetAddress getInetAddress(NetworkInterface networkInterface) {
		if (Objects.isNull(networkInterface)) {
			return null;
		}
		Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
		while (inetAddresses.hasMoreElements()) {
			InetAddress address = inetAddresses.nextElement();
			if (isValidAddress(address)) {
				return address;
			}
		}
		return null;
	}

	public static boolean isValidAddress(final InetAddress address) {
		if (address == null || address.isLoopbackAddress()) {
			return false;
		}
		final String name = address.getHostAddress();
		return name !=null && !ANYHOST.equals(name) && !LOCALHOST.equals(name) && IP_PATTERN.matcher(name).matches();
	}
}
