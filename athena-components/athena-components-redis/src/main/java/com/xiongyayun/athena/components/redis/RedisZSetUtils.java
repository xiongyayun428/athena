package com.xiongyayun.athena.components.redis;

import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

/**
 * 有序集合
 * 每个元素都会关联一个double类型的分数【注意是double类型分数】
 * redis正是通过分数来为集合中的成员进行从小到大的排序。zset的成员是唯一的,但分数(score)却可以重复。
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/3/8
 */
public class RedisZSetUtils extends RedisUtils {
	public Long removeZSetValue(String key, Object... value) {
		return this.redisTemplate.boundZSetOps(getRedisKey(key)).remove(value);
	}

	public void removeZSet(String key) {
		removeZSetRange(key, Long.valueOf(0L), Long.valueOf(getZSetSize(key)));
	}

	public void removeZSetRange(String key, Long start, Long end) {
		this.redisTemplate.boundZSetOps(getRedisKey(key)).removeRange(start.longValue(), end.longValue());
	}

	public void removeZSetRangeByScore(String key, double min, double max) {
		this.redisTemplate.boundZSetOps(getRedisKey(key)).removeRangeByScore(min, max);
	}

	public Set<Object> getZSetRange(String key) {
		return getZSetRange(key, 0L, getZSetSize(key));
	}

	public Set<Object> getZSetRange(String key, long start, long end) {
		return this.redisTemplate.boundZSetOps(getRedisKey(key)).range(start, end);
	}

	public Set<Object> getZSetReverseRange(String key) {
		return getZSetReverseRange(key, 0L, getZSetSize(key));
	}

	public <V> Set<V> getZSetReverseRange(String key, long start, long end) {
		return this.redisTemplate.boundZSetOps(getRedisKey(key)).reverseRange(start, end);
	}

	public long getZSetSize(String key) {
		return this.redisTemplate.boundZSetOps(getRedisKey(key)).size().longValue();
	}

	public Boolean setZSetExpireTime(String key, Long time) {
		return this.redisTemplate.boundZSetOps(getRedisKey(key)).expire(time.longValue(), TimeUnit.SECONDS);
	}

	public <V> Boolean addZSet(String key, double score, V value) {
		return this.redisTemplate.boundZSetOps(getRedisKey(key)).add(value, score);
	}

	public <V> Long addZSet(String key, TreeSet<V> value) {
		return this.redisTemplate.boundZSetOps(getRedisKey(key)).add(value);
	}

	public Boolean addZSet(String key, double[] score, Object[] value) {
		if (score.length != value.length) {
			return Boolean.valueOf(false);
		}
		for (int i = 0; i < score.length; i++) {
			if (!addZSet(key, score[i], value[i]).booleanValue()) {
				return Boolean.valueOf(false);
			}
		}
		return Boolean.valueOf(true);
	}
}
