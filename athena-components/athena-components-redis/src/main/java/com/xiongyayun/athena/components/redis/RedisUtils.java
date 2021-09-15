package com.xiongyayun.athena.components.redis;

import com.xiongyayun.athena.components.util.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * RedisUtils
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/2/26
 */
public class RedisUtils {
	protected static final Logger log = LoggerFactory.getLogger(RedisUtils.class);
	private static final String KEY_PREFIX = "ATHENA:::";
	private static final String KEY_SUFFIX = "";

	private static RedisUtils redisUtils;

//	@Resource
	protected RedisTemplate redisTemplate;


	public RedisTemplate getRedisTemplate() {
		return redisTemplate;
	}

	public RedisUtils() {
		this.redisTemplate = SpringContextUtil.getBean("redisTemplate");
	}

	public RedisUtils(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public static RedisUtils getInstance() {
		if (redisUtils == null) {
			synchronized (RedisUtils.class) {
				if (redisUtils == null) {
					redisUtils = new RedisUtils(SpringContextUtil.getBean("redisTemplate"));
				}
			}
		}
		return redisUtils;
	}

	public static RedisStringUtils getStringUtils() {
		return new RedisStringUtils();
	}


	protected String getRedisKey(String key) {
		return KEY_PREFIX + key + KEY_SUFFIX;
	}

	/**
	 * 设置过期时间
	 * @param key
	 * @param expireTime
	 * @return
	 */
	public boolean setExpireTime(String key, Long expireTime) {
		return this.redisTemplate.expire(getRedisKey(key), expireTime.longValue(), TimeUnit.SECONDS).booleanValue();
	}

	/**
	 * 设置过期日期
	 *
	 * @param key  key
	 * @param date 过期日期
	 *
	 * @return 是否成功
	 */
	public boolean setExpireAt(String key, Date date) {
		return redisTemplate.expireAt(getRedisKey(key), date).booleanValue();
	}

	/**
	 * 删除KEY
	 * @param key
	 */
	public void delete(String key) {
		if (exists(getRedisKey(key))) {
			this.redisTemplate.delete(getRedisKey(key));
		}
	}

	/**
	 * 批量删除key
	 *
	 * @param keys key集合
	 */
	public void delete(Collection<String> keys) {
		this.redisTemplate.delete(keys.stream().map(key -> getRedisKey(key)).collect(Collectors.toList()));
	}

	public void remove(String... key) {
		if (key != null && key.length > 0) {
			if (key.length == 1) {
				remove(key[0]);
			} else {
				this.redisTemplate.delete(CollectionUtils.arrayToList(key));
			}
		}
	}

	public void removeBlear(String... blears) {
		for (String blear : blears) {
			removeBlear(blear);
		}
	}

	public void removeBlear(String blear) {
		this.redisTemplate.delete(this.redisTemplate.keys(getRedisKey(blear)));
	}

	public void deleteByPattern(String pattern) {
		Set<String> keys = this.redisTemplate.keys(pattern);
		if (!CollectionUtils.isEmpty(keys)) {
			this.redisTemplate.delete(keys);
		}
	}

	/**
	 * 查找匹配的key
	 * @param pattern	匹配规则
	 * @return			结果集合
	 */
	public Set<String> keys(String pattern) {
		return redisTemplate.keys(pattern);
	}

	/**
	 * 是否存在key
	 * @param key
	 * @return
	 */
	public boolean exists(String key) {
		return this.redisTemplate.hasKey(getRedisKey(key)).booleanValue();
	}

	/**
	 * 序列化key
	 * @param key
	 * @return
	 */
	public byte[] dump(String key) {
		return redisTemplate.dump(getRedisKey(key));
	}

	public void watch(String key) {
		this.redisTemplate.watch(getRedisKey(key));
	}

	public DataType getType(String key) {
		return this.redisTemplate.type(getRedisKey(key));
	}

	/**
	 * 将当前数据库的 key 移动到给定的数据库 db 当中
	 *
	 * @param key     key
	 * @param dbIndex 目标DB
	 *
	 * @return 是否成功
	 */
	public boolean move(String key, int dbIndex) {
		return redisTemplate.move(getRedisKey(key), dbIndex).booleanValue();
	}

	/**
	 * 移除 key 的过期时间，key 将持久保持
	 *
	 * @param key key
	 *
	 * @return 是否成功
	 */
	public boolean persist(String key) {
		return redisTemplate.persist(getRedisKey(key)).booleanValue();
	}

	/**
	 * 返回 key 的剩余的过期时间（指定单位）
	 *
	 * @param key  key
	 * @param unit 时间单位
	 *
	 * @return 过期时间
	 */
	public Long getExpire(String key, TimeUnit unit) {
		return redisTemplate.getExpire(getRedisKey(key), unit);
	}

	/**
	 * 返回 key 的剩余的过期时间（s）
	 *
	 * @param key key
	 *
	 * @return 过期时间
	 */
	public Long getExpire(String key) {
		return redisTemplate.getExpire(getRedisKey(key));
	}

	/**
	 * 从当前数据库中随机返回一个 key
	 *
	 * @return 随机的 key
	 */
	public String randomKey() {
		return String.valueOf(redisTemplate.randomKey());
	}

	/**
	 * 修改 key 的名称
	 *
	 * @param oldKey 原 key 名称
	 * @param newKey 新 key 名称
	 */
	public void rename(String oldKey, String newKey) {
		redisTemplate.rename(getRedisKey(oldKey), getRedisKey(newKey));
	}

	/**
	 * 仅当 newkey 不存在时，将 oldKey 改名为 newkey
	 * @param oldKey
	 * @param newKey
	 * @return
	 */
	public Boolean renameIfAbsent(String oldKey, String newKey) {
		return this.redisTemplate.renameIfAbsent(getRedisKey(oldKey), getRedisKey(newKey));
	}
}
