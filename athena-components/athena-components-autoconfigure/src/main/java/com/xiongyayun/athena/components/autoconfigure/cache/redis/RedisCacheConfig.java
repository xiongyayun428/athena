package com.xiongyayun.athena.components.autoconfigure.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.xiongyayun.athena.components.redis.cache.AthenaRedisCacheManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * RedisCacheConfig
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/2/26
 */
@Configuration
@EnableCaching
public class RedisCacheConfig extends CachingConfigurerSupport {
	@Resource
	private ResourceLoader resourceLoader;

	@ConditionalOnMissingBean(CacheManager.class)
	@ConditionalOnBean(RedisConnectionFactory.class)
	@Bean
	public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
		RedisCacheConfiguration defaultCacheConfig = this.getRedisCacheConfigurationWithTtl(Duration.ofDays(1))
//				.computePrefixWith(cacheName -> "caching:" + cacheName)
				;
		return new AthenaRedisCacheManager(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory), defaultCacheConfig);

//		return RedisCacheManager.builder(redisConnectionFactory)
//				// 默认的缓存配置(没有配置键的key均使用此配置)
//				.cacheDefaults(getDefaultCacheConfiguration())
//				// 不同cache的个性化配置
//				.withInitialCacheConfigurations(getRedisCacheConfigurationMap())
//				// 在spring事务正常提交时才缓存数据
//				.transactionAware()
//				.build();
	}
	/**
	 * 获取Redis缓存配置,此处获取的为默认配置
	 * 如对键值序列化方式,是否缓存null值,是否使用前缀等有特殊要求
	 * 可另行调用 RedisCacheConfiguration 的构造方法
	 *
	 * @return
	 */
	private RedisCacheConfiguration getDefaultCacheConfiguration() {
		// 注意此构造函数为 spring-data-redis-2.1.9 及以上拥有,经试验 已知spring-data-redis-2.0.9及以下版本没有此构造函数
		// 但观察源码可得核心不过是在值序列化器(valueSerializationPair)的构造中注入 ClassLoader 即可
		return RedisCacheConfiguration.defaultCacheConfig(resourceLoader.getClassLoader());
	}

	/**
	 * 获取redis的缓存配置(针对于键)
	 * @param ttl 键过期时间
	 * @return
	 */
	private RedisCacheConfiguration getRedisCacheConfigurationWithTtl(Duration ttl) {
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
		redisCacheConfiguration = redisCacheConfiguration.serializeValuesWith(
				RedisSerializationContext
						.SerializationPair
						.fromSerializer(jackson2JsonRedisSerializer)
		).entryTtl(ttl);
		return redisCacheConfiguration;
	}

	private Map<String, RedisCacheConfiguration> getRedisCacheConfigurationMap() {
		Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
		//SsoCache和BasicDataCache进行过期时间配置
//		redisCacheConfigurationMap.put("messagCache", this.getRedisCacheConfigurationWithTtl(30 * 60));

		//自定义设置缓存时间
//		redisCacheConfigurationMap.put("studentCache", this.getRedisCacheConfigurationWithTtl(60 ));

		return redisCacheConfigurationMap;
	}
}
