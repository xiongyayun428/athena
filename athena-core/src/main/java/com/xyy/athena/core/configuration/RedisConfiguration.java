//package com.xyy.athena.core.configuration;
//
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
///**
// * RedisConfiguration
// *
// * @author: 熊亚运
// * @date: 2019-06-17
// */
//@Configuration
//@EnableCaching
//public class RedisConfiguration {
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//
//        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
//        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
//
//        //使用StringRedisSerializer来序列化和反序列化redis的ke
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//
//        //开启事务
//        redisTemplate.setEnableTransactionSupport(true);
//
//        redisTemplate.setConnectionFactory(redisConnectionFactory);
//
//        return redisTemplate;
//    }
//}
