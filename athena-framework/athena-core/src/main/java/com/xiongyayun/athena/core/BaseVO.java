package com.xiongyayun.athena.core;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * BaseVO
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2020/12/24
 */
public abstract class BaseVO implements Serializable {
	@ApiModelProperty("当前页")
	private Long pageIndex;
	@ApiModelProperty("每页显示条数")
	private Long pageSize;
	@ApiModelProperty("排序")
	private KeyValueVO[] sort;

	public Long getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Long pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Long getPageSize() {
		return pageSize;
	}

	public void setPageSize(Long pageSize) {
		this.pageSize = pageSize;
	}

	public KeyValueVO[] getSort() {
		return sort;
	}

	public void setSort(KeyValueVO[] sort) {
		this.sort = sort;
	}

	public class KeyValueVO implements Serializable {
		@ApiModelProperty("排序字段")
		private String key;
		@ApiModelProperty("排序方式")
		private String value;

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
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
		}

		@Override
		public String toString() {
			return "KeyValueVO{" +
					"key='" + key + '\'' +
					", value='" + value + '\'' +
					'}';
		}
	}
}
