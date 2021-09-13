package com.xiongyayun.athena.components.redis;

import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * Hash（哈希，类似java里的Map）
 * 特别适合用于存储对象 类似Java里面的Map<String,Object>
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/3/8
 */
public class RedisHashUtils extends RedisUtils {
	public void removeMapField(String key, Object... field) {
		this.redisTemplate.boundHashOps(getRedisKey(key)).delete(field);
	}

	public Long getMapSize(String key) {
		return this.redisTemplate.boundHashOps(getRedisKey(key)).size();
	}

	public Map getMap(String key) {
		return this.redisTemplate.boundHashOps(getRedisKey(key)).entries();
	}

	public <T> T getMapField(String key, String field) {
		if (!StringUtils.hasLength(field)) {
			return null;
		}
		return (T)this.redisTemplate.boundHashOps(getRedisKey(key)).get(field);
	}

	public Boolean hasMapKey(String key, String field) {
		return this.redisTemplate.boundHashOps(getRedisKey(key)).hasKey(field);
	}

	public List<Object> getMapFieldValue(String key) {
		return this.redisTemplate.boundHashOps(getRedisKey(key)).values();
	}

	public Set<Object> getMapFieldKey(String key) {
		return this.redisTemplate.boundHashOps(getRedisKey(key)).keys();
	}

	public void addMap(String key, Map map) {
		this.redisTemplate.boundHashOps(getRedisKey(key)).putAll(map);
	}

	public Long putHashMap(String key, Map map, long time) {
		addMap(key, map);
		if (time > 0L) {
			setExpireTime(key, Long.valueOf(time));
		}
		return getMapSize(key);
	}

	public void addMap(String key, String field, Object value) {
		this.redisTemplate.boundHashOps(getRedisKey(key)).put(field, value);
	}

	public void addMap(String key, String field, Object value, long time) {
		this.redisTemplate.boundHashOps(getRedisKey(key)).put(field, value);
		this.redisTemplate.boundHashOps(getRedisKey(key)).expire(time, TimeUnit.SECONDS);
	}

	public void removeMapFieldByRegular(String key, String blear) {
		Map<String, Object> map = getMap(key);
		Set<String> stringSet = map.keySet();
		for (String s : stringSet) {
			if (Pattern.compile(blear).matcher(s).matches()) {
				this.redisTemplate.boundHashOps(getRedisKey(key)).delete(new Object[]{s});
			}
		}
	}
}
