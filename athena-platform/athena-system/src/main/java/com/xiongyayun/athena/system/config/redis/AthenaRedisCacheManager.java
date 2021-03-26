package com.xiongyayun.athena.system.config.redis;

import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.util.StringUtils;

import java.time.Duration;

/**
 * AthenaRedisCacheManager
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/2/26
 */
public class AthenaRedisCacheManager extends RedisCacheManager {
	public AthenaRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
		super(cacheWriter, defaultCacheConfiguration);
	}

	@Override
	protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {
		String[] array = StringUtils.delimitedListToStringArray(name, "#");
		name = array[0];
		if (array.length > 1) { // 解析TTL
			long ttl = Long.parseLong(array[1]);
			cacheConfig = cacheConfig.entryTtl(Duration.ofSeconds(ttl)); // 注意单位我此处用的是秒，而非毫秒
		}
		return super.createRedisCache(name, cacheConfig);
	}
}
