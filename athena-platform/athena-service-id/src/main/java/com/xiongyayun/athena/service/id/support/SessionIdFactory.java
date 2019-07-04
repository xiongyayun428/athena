package com.xiongyayun.athena.service.id.support;

import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * <p><b>会话ID工厂</b></p>
 * 不依靠数据库，随机产生(线程安全)
 *
 * @author: Yayun.Xiong
 * @date 2019-04-14
 */
@Service
public class SessionIdFactory extends AbstractIdFactory {
	private static Random _randomNumberGenerator;
	private static int counter = 0;
	private static long lastTimeVal = 0L;
	protected static final int DIGIT_BASE = 36;
	protected static final long MAX_RANDOM_LEN = 2176782336L;
	protected static final long MAX_SESSION_LIFESPAN_TICS = 46656L;
	protected static final long TIC_DIFFERENCE = 2000L;

	static {
		try {
			_randomNumberGenerator = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {
			_randomNumberGenerator = new SecureRandom();
		}
	}

	@Override
	public Object generate() {
		long n = _randomNumberGenerator.nextLong();
		if (n < 0L) {
			n = -n;
		}
		StringBuffer id = new StringBuffer();

		n %= MAX_RANDOM_LEN;
		n += MAX_RANDOM_LEN;
		id.append(Long.toString(n, DIGIT_BASE).substring(1).toUpperCase());

		long timeVal = System.currentTimeMillis() / TIC_DIFFERENCE;
		timeVal %= MAX_SESSION_LIFESPAN_TICS;
		timeVal += MAX_SESSION_LIFESPAN_TICS;
		id.append(Long.toString(timeVal, DIGIT_BASE).substring(1).toUpperCase());
		synchronized (this) {
			if (lastTimeVal != timeVal) {
				lastTimeVal = timeVal;
				counter = 0;
			}
			counter += 1;
		}
		id.append(Long.toString(counter, DIGIT_BASE));
		return id.toString();
	}

}
