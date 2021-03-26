package com.xiongyayun.athena.core;

/**
 * KeyValueVO
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/3/12
 */
public class KeyValueVO {
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
		switch (value) {
			case "ascend":
				this.value = "ASC";
				break;
			case "descend":
				this.value = "DESC";
				break;
			default:
				throw new IllegalArgumentException("排序参数错误");
		}
		return this;
	}
}
