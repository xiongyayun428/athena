package com.xiongyayun.athena.core;

import java.io.Serializable;

/**
 * KeyValueVO
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/3/12
 */
public class KeyValueVO implements Serializable {
	private String key;
	private String value;

	public String getKey() {
		return key;
	}

	public KeyValueVO setKey(String key) {
		this.key = key;
		return this;
	}

	public String getValue() {
		return value;
	}

	public KeyValueVO setValue(String value) {
		switch (value.toLowerCase()) {
			case "ascend":
			case "asc":
				this.value = "ASC";
				break;
			case "descend":
			case "desc":
				this.value = "DESC";
				break;
			default:
				throw new IllegalArgumentException("排序参数错误");
		}
		return this;
	}

	@Override
	public String toString() {
		return "KeyValueVO{" +
				"key='" + key + '\'' +
				", value='" + value + '\'' +
				'}';
	}
}
