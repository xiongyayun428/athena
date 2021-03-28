package com.xiongyayun.athena.core.redis;

import org.springframework.data.redis.core.BoundValueOperations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * Redis中字符串value最多可以是512M
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/3/8
 */
public class RedisStringUtils extends RedisUtils {

	/**
	 * 给绑定键重新设置值
	 * @param key
	 * @param value
	 */
	public <V> void set(long key, V value) {
		set(String.valueOf(key), value);
	}

	/**
	 * 给绑定键重新设置值
	 * @param key
	 * @param value
	 */
	public <V> void set(int key, V value) {
		set(String.valueOf(key), value);
	}

	/**
	 * 给绑定键重新设置值
	 * @param key
	 * @param value
	 */
	public <V> void set(String key, V value) {
		this.redisTemplate.boundValueOps(getRedisKey(key)).set(value);
	}

	/**
	 * 在指定时间后重新设置值
	 * @param key
	 * @param value
	 * @param timeout	指定时间
	 * @param timeUnit
	 */
	public <V> void set(String key, V value, Long timeout, TimeUnit timeUnit) {
		this.redisTemplate.boundValueOps(getRedisKey(key)).set(value, timeout.longValue(), timeUnit);
	}

	/**
	 * 截取原有值的指定长度后添加新值在后面
	 * @param key
	 * @param value
	 * @param offset
	 */
	public <V> void set(String key, V value, Long offset) {
		this.redisTemplate.boundValueOps(getRedisKey(key)).set(value, offset.longValue());
	}

	/**
	 * 返回 key 中字符串值的子字符
	 * @param key
	 * @param <T>
	 * @return
	 */
	public <T> T get(String key) {
		try {
			BoundValueOperations<String, T> ops = this.redisTemplate.boundValueOps(getRedisKey(key));
			return ops.get();
		} catch (Exception e) {
			log.warn("get from Cache error! key = {" + key + "}", e);
			return null;
		}
	}

	public <T> T get(String key, Function<String, T> mappingFunction) {
		try {
			BoundValueOperations<String, T> ops = this.redisTemplate.boundValueOps(getRedisKey(key));
			T value = ops.get();
			if (value == null) {
				synchronized (key.intern()) {
					value = ops.get();
					if (value == null) {
						value = mappingFunction.apply(key);
						if (value != null) {
							ops.set(value);
						}
					}
				}
			}
			return value;
		} catch (Exception e) {
			log.warn("get from Cache error! key = {" + key + "}", e);
			return null;
		}
	}

	public String get(String key, long start, long end) {
		try {
			return this.redisTemplate.opsForValue().get(getRedisKey(key), start, end);
		} catch (Exception e) {
			log.warn("get from Cache error! key = {" + key + "}", e);
			return null;
		}
	}

	/**
	 * 获取多个指定 key 的值
	 * @param keys
	 * @param <T>
	 * @return
	 */
	public <T> List<T> get(String... keys) {
		List<T> list = new ArrayList();
		for (String key : keys) {
			list.add(get(key));
		}
		return list;
	}

	/**
	 * 获取多个指定 key 的值
	 * @param keys
	 * @param <T>
	 * @return
	 */
	public <T> List<T> get(Collection<String> keys) {
		List<T> list = new ArrayList();
		for (String key : keys) {
			list.add(get(key));
		}
		return list;
	}

	/**
	 * 没有值存在则添加
	 * @param key
	 * @param value
	 */
	public void setIfAbsent(String key, Object value) {
		this.redisTemplate.boundValueOps(getRedisKey(key)).setIfAbsent(value);
	}

	public long incr(String key) {
		return this.redisTemplate.boundValueOps(getRedisKey(key)).increment().longValue();
	}

	/**
	 * 增加(自增长), 负数则为自减
	 * @param key
	 * @param increment
	 * @return				变化后的值
	 */
	public long incr(String key, long increment) {
		return this.redisTemplate.boundValueOps(getRedisKey(key)).increment(increment).longValue();
	}

	public double incr(String key, double increment) {
		return this.redisTemplate.boundValueOps(getRedisKey(key)).increment(increment).doubleValue();
	}

	public long decrement(String key) {
		return this.redisTemplate.boundValueOps(getRedisKey(key)).decrement().longValue();
	}
	public long decrement(String key, long delta) {
		return this.redisTemplate.boundValueOps(getRedisKey(key)).decrement(delta).longValue();
	}

	/**
	 * 追加到末尾
	 * @param key
	 * @param value
	 * @return
	 */
	public Integer append(String key, String value) {
		return this.redisTemplate.boundValueOps(getRedisKey(key)).append(value);
	}
}
