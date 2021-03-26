package com.xiongyayun.athena.core;

import io.swagger.annotations.ApiModelProperty;

/**
 * BaseVO
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2020/12/24
 */
public abstract class BaseVO {
	@ApiModelProperty("当前页")
	private Long pageIndex;
	@ApiModelProperty("每页显示条数")
	private Long pageSize;
	@ApiModelProperty("起始时间")
	private String startDate;
	@ApiModelProperty("结束时间")
	private String endDate;
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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public KeyValueVO[] getSort() {
		return sort;
	}

	public void setSort(KeyValueVO[] sort) {
		this.sort = sort;
	}
}
