package com.xiongyayun.athena.core.utils;

import com.baomidou.mybatisplus.annotation.TableField;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;

/**
 * ClassUtil
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/3/12
 */
public class ClassUtil {
	private static Field findField(Class clazz, String name) {
		try {
			return clazz.getDeclaredField(name);
		} catch (Exception e) {
			return findField(clazz.getSuperclass(), name);
		}
	}

	public static String getFieldName(Class clazz, String name) {
		try {
			Field field = findField(clazz, name);
			if (field == null) {
				throw new IllegalArgumentException("字段不存在");
			}
			TableField tf = field.getAnnotation(TableField.class);
			if (tf != null) {
				String tName = tf.value();
				if (StringUtils.hasLength(tName)) {
					return tName;
				}
			}
			return com.baomidou.mybatisplus.core.toolkit.StringUtils.camelToUnderline(name);
		} catch (Exception e) {
			throw new IllegalArgumentException("字段不存在");
		}
	}

	// 把一个字符串的第一个字母大写、效率是最高的、
	public static String getMethodName(String fieldName) throws Exception {
		byte[] items = fieldName.getBytes();
		items[0] = (byte) ((char) items[0] - 'a' + 'A');
		return new String(items);
	}
}
