package com.xiongyayun.athena.components.redis;

import com.xiongyayun.athena.components.util.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * RedisLock
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/3/8
 */
public class RedisLock {
	private static final Logger log = LoggerFactory.getLogger(RedisLock.class);

	private static final RedisTemplate redisTemplate = SpringContextUtil.getBean("redisTemplate");

	private static final int DEFAULT_ACQUIRY_RESOLUTION_MILLIS = 100;

	private String lockKey;

	private int expireMsecs = 60000;

	private int timeoutMsecs = 10000;

	private volatile boolean locked = false;

	public RedisLock(String lockKey) {
		this.lockKey = lockKey + "_lock";
	}

	public RedisLock(String lockKey, int timeoutMsecs) {
		this(lockKey);
		this.timeoutMsecs = timeoutMsecs;
	}

	public RedisLock(String lockKey, int timeoutMsecs, int expireMsecs) {
		this(lockKey, timeoutMsecs);
		this.expireMsecs = expireMsecs;
	}

	private String get(final String key) {
		Object obj = null;
		try {
			obj = redisTemplate.execute(new RedisCallback<Object>() {
				@Override
				public Object doInRedis(RedisConnection connection) throws DataAccessException {
					StringRedisSerializer serializer = new StringRedisSerializer();
					byte[] data = connection.get(serializer.serialize(key));
					connection.close();
					if (data == null) {
						return null;
					}
					return serializer.deserialize(data);
				}
			});
		} catch (Exception e) {
			log.error("get redis error, key : {" + key + "}");
		}
		return (obj != null) ? obj.toString() : null;
	}

	private boolean setNX(final String key, final String value) {
		Object obj = null;
		try {
			obj = redisTemplate.execute((RedisCallback<Object>) connection -> {
				StringRedisSerializer serializer = new StringRedisSerializer();
				Boolean success = connection.setNX(serializer.serialize(key), serializer.serialize(value));
				connection.close();
				return success;
			});
		} catch (Exception e) {
			log.error("setNX redis error, key : {" + key + "}");
		}
		return (obj != null) ? ((Boolean)obj).booleanValue() : false;
	}

	private String getSet(final String key, final String value) {
		Object obj = null;
		try {
			obj = redisTemplate.execute((RedisCallback<Object>) connection -> {
				StringRedisSerializer serializer = new StringRedisSerializer();
				byte[] ret = connection.getSet(serializer.serialize(key), serializer.serialize(value));
				connection.close();
				return serializer.deserialize(ret);
			});
		} catch (Exception e) {
			log.error("setNX redis error, key : {" + key + "}");
		}
		return (obj != null) ? (String)obj : null;
	}

	public synchronized boolean lock() throws InterruptedException {
		int timeout = this.timeoutMsecs;
		while (timeout >= 0) {
			long expires = System.currentTimeMillis() + this.expireMsecs + 1L;
			String expiresStr = String.valueOf(expires);
			if (setNX(this.lockKey, expiresStr)) {
				this.locked = true;
				return true;
			}
			String currentValueStr = get(this.lockKey);
			if (currentValueStr != null && Long.parseLong(currentValueStr) < System.currentTimeMillis()) {
				String oldValueStr = getSet(this.lockKey, expiresStr);
				if (oldValueStr != null && oldValueStr.equals(currentValueStr)) {
					this.locked = true;
					return true;
				}
			}
			timeout -= DEFAULT_ACQUIRY_RESOLUTION_MILLIS;
			Thread.sleep(100L);
		}
		return false;
	}

	public synchronized void unlock() {
		if (this.locked) {
			redisTemplate.delete(this.lockKey);
			this.locked = false;
		}
	}

//	public static boolean acquireLock(Jedis jedis, String lock) {
//		boolean success = false;
//		long value = System.currentTimeMillis() + 86400000L + 1L;
//		long acquired = jedis.setnx(lock, String.valueOf(value)).longValue();
//		jedis.expire(lock, 1);
//		if (acquired == 1L)
//			success = true;
//		return success;
//	}
//
//	public static void releaseLock(Jedis jedis, String lock) {
//		jedis.del(lock);
//	}

	public String getLockKey() {
		return this.lockKey;
	}
}
