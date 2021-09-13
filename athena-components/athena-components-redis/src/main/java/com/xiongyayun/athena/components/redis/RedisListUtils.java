package com.xiongyayun.athena.components.redis;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 底层实际是个链表
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/3/8
 */
public class RedisListUtils extends RedisUtils {
	public void addList(String key, List<Object> objectList) {
		for (Object obj : objectList) {
			addList(key, obj);
		}
	}

	public <V> long addList(String key, V obj) {
		return this.redisTemplate.boundListOps(getRedisKey(key)).rightPush(obj).longValue();
	}

	public <V> List<V> getList(String key, long start, long end) {
		return this.redisTemplate.boundListOps(getRedisKey(key)).range(start, end);
	}

	public <V> List<V> getList(String key) {
		return this.redisTemplate.boundListOps(getRedisKey(key)).range(0L, getListSize(key));
	}

	public long getListSize(String key) {
		return this.redisTemplate.boundListOps(getRedisKey(key)).size().longValue();
	}

	public long removeListValue(String key, Object value) {
		return this.redisTemplate.boundListOps(getRedisKey(key)).remove(0L, value).longValue();
	}

	public long removeListValue(String key, Object... objects) {
		long r = 0L;
		for (Object value : objects) {
			r += removeListValue(key, value);
		}
		return r;
	}
	public void removeOpsList(String key) {
		while (this.redisTemplate.opsForList().size(key).longValue() > 0L) {
			this.redisTemplate.opsForList().leftPop(key);
		}
	}

	public void addOpsList(String key, Object object) {
		if (object instanceof List) {
			this.redisTemplate.opsForList().rightPushAll(key, (List)object);
		} else {
			this.redisTemplate.opsForList().rightPushAll(key, new Object[] { object });
		}
	}

	public <T> T getOpsList(String key) {
		return (T)this.redisTemplate.opsForList().range(key, 0L, -1L);
	}

	public void pushQueue(String key, Object object) {
		this.redisTemplate.opsForList().leftPush(getRedisKey(key), object);
	}

	public <T> T popQueue(String key) {
		try {
			String keyStr = getRedisKey(key);
			if (this.redisTemplate.opsForList().size(keyStr).longValue() > 0L) {
				return (T) this.redisTemplate.opsForList().rightPop(keyStr, 1L, TimeUnit.SECONDS);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
}
