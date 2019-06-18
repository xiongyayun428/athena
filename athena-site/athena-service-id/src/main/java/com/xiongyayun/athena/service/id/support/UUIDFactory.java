package com.xiongyayun.athena.service.id.support;

import lombok.Setter;
import org.springframework.stereotype.Service;

import java.net.InetAddress;

/**
 * UUID生成器
 *
 * @author Yayun.Xiong
 * @date 2019-04-14 16:41
 */
@Service
public class UUIDFactory extends AbstractIdFactory {
	@Setter
	private String separator = "-";
	private static final int IP;
	private static final int JVM = (int) (System.currentTimeMillis() >>> 8);
	private static short counter = 0;

	static {
		int ipAdd = 0;
		try {
			byte[] ips = InetAddress.getLocalHost().getAddress();
			for (int i = 0; i < 4; i++) {
				ipAdd = (ipAdd << 8) - -128 + ips[i];
			}
		} catch (Exception e) {
			ipAdd = 0;
		}
		IP = ipAdd;
	}

	@Override
	public Object generate() {
		return (36 + format(IP) + this.separator
				+ format(JVM) + this.separator
				+ format((short) (int) (System.currentTimeMillis() >>> 32)) + this.separator
				+ format((int) System.currentTimeMillis()) + this.separator
				+ format(count()))
				.toUpperCase();
	}

	protected String format(int intval) {
		String formatted = Integer.toHexString(intval);
		StringBuffer buf = new StringBuffer("00000000");
		buf.replace(8 - formatted.length(), 8, formatted);
		return buf.toString();
	}

	protected String format(short shortval) {
		String formatted = Integer.toHexString(shortval);
		StringBuffer buf = new StringBuffer("0000");
		buf.replace(4 - formatted.length(), 4, formatted);
		return buf.toString();
	}

	private short count() {
		synchronized (this) {
			if (counter < 0) {
				counter = 0;
			}
			short tmp = counter;
			counter = (short) (tmp + 1);
			return tmp;
		}
	}

	public static void main(String[] args) {
		UUIDFactory a = new UUIDFactory();
		for (int i = 0; i < 10; i++) {
			System.err.println(a.generate());
		}
		System.out.println("------------");
		Object[] b = a.generate(5);
		System.out.println(b.length);
	}

}
