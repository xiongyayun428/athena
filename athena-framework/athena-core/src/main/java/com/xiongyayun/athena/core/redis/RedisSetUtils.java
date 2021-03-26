package com.xiongyayun.athena.core.redis;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis的Set是string类型的无序集合。它是通过HashTable实现实现的，保证唯一性
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/3/8
 */
public class RedisSetUtils extends RedisUtils {

	public <V> void addSet(String key, V... values) {
		this.redisTemplate.boundSetOps(getRedisKey(key)).add(values);
	}

	public long removeSetValue(String key, Object value) {
		return this.redisTemplate.boundSetOps(getRedisKey(key)).remove(new Object[] { value }).longValue();
	}

	public long removeSetValue(String key, Object... values) {
		if (values != null && values.length > 0) {
			return this.redisTemplate.boundSetOps(getRedisKey(key)).remove(values).longValue();
		}
		return 0L;
	}

	public long getSetSize(String key) {
		return this.redisTemplate.boundSetOps(getRedisKey(key)).size().longValue();
	}
	public Boolean setSetExpireTime(String key, Long time) {
		return this.redisTemplate.boundSetOps(getRedisKey(key)).expire(time.longValue(), TimeUnit.SECONDS);
	}

	public Boolean hasSetValue(String key, Object obj) {
		Boolean boo = null;
		int t = 0;
		while (true) {
			try {
				boo = this.redisTemplate.boundSetOps(getRedisKey(key)).isMember(obj);
				break;
			} catch (Exception e) {
				this.log.error("key[" + key + "],obj[" + obj + "]" + e.getMessage());
				t++;
				if (t > 3) {
					break;
				}
			}
		}
		this.log.info("key[" + key + "],obj[" + obj + "]" + boo);
		return boo;
	}
	public <V> Set<V> getSet(String key) {
		return this.redisTemplate.boundSetOps(getRedisKey(key)).members();
	}

	public <V> Set<V> getSetUnion(String key, String otherKey) {
		return this.redisTemplate.boundSetOps(getRedisKey(key)).union(otherKey);
	}

	public <V> Set<V> getSetUnion(String key, Set<Object> set) {
		return this.redisTemplate.boundSetOps(getRedisKey(key)).union(set);
	}

	public <V> Set<V> getSetIntersect(String key, String otherKey) {
		return this.redisTemplate.boundSetOps(getRedisKey(key)).intersect(otherKey);
	}

	public <V> Set<V> getSetIntersect(String key, Set<Object> set) {
		return this.redisTemplate.boundSetOps(getRedisKey(key)).intersect(set);
	}
}
