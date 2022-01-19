package com.xiongyayun.athena.components.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * NetUtil
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/09/12
 */
public class NetUtils {
	public static final String LOCALHOST = "127.0.0.1";
	private static final String FAILED_TO_RETRIVING_IP_ADDRESS = "Failed to retriving ip address";
	private static final Logger logger = LoggerFactory.getLogger(NetUtils.class);
	private static final String ANYHOST = "0.0.0.0";
	private static final Pattern IP_PATTERN = Pattern.compile("\\d{1,3}(\\.\\d{1,3}){3,5}$");
	private static InetAddress localAddress;

	private NetUtils() {

	}

	@Nullable
	public static InetAddress getLocalAddress() {
		if (localAddress != null) {
			return localAddress;
		}
		localAddress = getLocalAddress0();
		return localAddress;
	}

	@Nullable
	public static String getLocalIpAddress() {
		return Optional.ofNullable(getLocalAddress())
				.map(InetAddress::getHostAddress)
				.orElse(NetUtils.LOCALHOST);
	}

	public static boolean isValidAddress(final InetAddress address) {
		if (address == null || address.isLoopbackAddress()) {
			return false;
		}
		final String name = address.getHostAddress();
		return name != null && !ANYHOST.equals(name) && !LOCALHOST.equals(name) && NetUtils.IP_PATTERN.matcher(name).matches();
	}

	public static InetAddress getLocalAddress0() {
		try {
			InetAddress localAddress = InetAddress.getLocalHost();
			if (isValidAddress(localAddress)) {
				return localAddress;
			}
		} catch (Exception e) {
			logger.warn(FAILED_TO_RETRIVING_IP_ADDRESS, e);
		}
		return getAddressByNetwork();
	}

	private static InetAddress getAddressByNetwork() {
		try {
			final Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			if (Objects.isNull(interfaces)) {
				return null;
			}
			return getAddressByInterfaces(interfaces);
		} catch (Exception e) {
			logger.warn(FAILED_TO_RETRIVING_IP_ADDRESS, e);
		}
		logger.error("Could not get local host ip address, will use 127.0.0.1 instead.");
		return null;
	}

	private static InetAddress getAddressByInterfaces(Enumeration<NetworkInterface> interfaces) {
		while (interfaces.hasMoreElements()) {
			try {
				final NetworkInterface  networkInterface = interfaces.nextElement();
				InetAddress address = getAddressByNetworkInterfaces(networkInterface);
				if (Objects.nonNull(address)) {
					return address;
				}
			} catch (Exception e) {
				logger.warn(FAILED_TO_RETRIVING_IP_ADDRESS, e);
			}
		}
		return null;
	}

	private static InetAddress getAddressByNetworkInterfaces(NetworkInterface networkInterface) {
		final Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
		while (addresses.hasMoreElements()) {
			try {
				final InetAddress address = addresses.nextElement();
				if (isValidAddress(address)) {
					return address;
				}
			} catch (Exception e) {
				logger.warn(FAILED_TO_RETRIVING_IP_ADDRESS, e);
			}
		}
		return null;
	}
}
