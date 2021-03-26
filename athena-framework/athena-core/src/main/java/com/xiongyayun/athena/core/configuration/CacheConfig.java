//package com.xiongyayun.athena.core.configuration;
//
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ResourceLoader;
//import org.springframework.data.redis.cache.RedisCacheConfiguration;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.cache.RedisCacheWriter;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializationContext;
//
//import javax.annotation.Resource;
//import java.time.Duration;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * CacheConfig
// *
// * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
// * @date 2021/2/26
// */
//@Configuration
//@EnableCaching
//public class CacheConfig {
//	@Resource
//	private ResourceLoader resourceLoader;
//
//	/*@Bean
//    public ConcurrentMapCacheManager cacheManager() {
//        ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager();
//        //cacheManager.setStoreByValue(true); //true表示缓存一份副本，否则缓存引用
//        return cacheManager;
//    }*/
//
//	/**
//	 * 最新版，设置redis缓存过期时间
//	 */
//
//	@Bean
//	public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
//		return new RedisCacheManager(
//				RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory),
//				this.getRedisCacheConfigurationWithTtl( 60), // 默认策略，未配置的 key 会使用这个
//				this.getRedisCacheConfigurationMap() // 指定 key 策略
//		);
//	}
//
//	private Map<String, RedisCacheConfiguration> getRedisCacheConfigurationMap() {
//		Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
//		//SsoCache和BasicDataCache进行过期时间配置
//		redisCacheConfigurationMap.put("messagCache", this.getRedisCacheConfigurationWithTtl(30 * 60));
//
//		//自定义设置缓存时间
//		redisCacheConfigurationMap.put("studentCache", this.getRedisCacheConfigurationWithTtl(60 ));
//
//		return redisCacheConfigurationMap;
//	}
//
//	private RedisCacheConfiguration getRedisCacheConfigurationWithTtl(Integer seconds) {
//		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
//		ObjectMapper om = new ObjectMapper();
//		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//		om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
//		jackson2JsonRedisSerializer.setObjectMapper(om);
//		RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
//		redisCacheConfiguration = redisCacheConfiguration.serializeValuesWith(
//				RedisSerializationContext
//						.SerializationPair
//						.fromSerializer(jackson2JsonRedisSerializer)
//		).entryTtl(Duration.ofSeconds(seconds));
//
//		return redisCacheConfiguration;
//	}
//
//}
