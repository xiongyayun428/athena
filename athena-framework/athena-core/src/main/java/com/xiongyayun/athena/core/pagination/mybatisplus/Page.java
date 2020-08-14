package com.xiongyayun.athena.core.pagination.mybatisplus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * 分页模型
 * {
 *     "total": 0,
 *     "pageCount": 0,
 *     "PageIndex": 1,
 *     "pageSize": 10,
 *     "hasPrevious": false,
 *     "hasNext": false,
 *     "records": []
 * }
 *
 * @author: <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date: 2020/8/6
 */
@JsonInclude()
@JsonIgnoreProperties({ "pages", "current", "size", "optimizeCountSql", "searchCount", "hitCount", "orders" })
@JsonPropertyOrder({"total", "pageCount", "pageIndex", "pageSize", "hasPrevious", "hasNext", "records"})
public class Page<T> extends com.baomidou.mybatisplus.extension.plugins.pagination.Page<T> implements com.xiongyayun.athena.core.pagination.IPage<T> {

	public Page() {
	}

	/**
	 * 分页构造函数
	 *
	 * @param pageIndex   当前页数
	 * @param pageSize    每页显示条数
	 */
	public Page(long pageIndex, long pageSize) {
		this(pageIndex, pageSize, 0);
	}

	public Page(long pageIndex, long pageSize, long total) {
		this(pageIndex, pageSize, total, true);
	}

	public Page(long pageIndex, long pageSize, boolean searchCount) {
		this(pageIndex, pageSize, 0, searchCount);
	}

	public Page(long pageIndex, long pageSize, long total, boolean searchCount) {
		super(pageIndex, pageSize, total, searchCount);
	}

	/**
	 * 当前页
	 * @return
	 */
	@Override
	public long getPageIndex() {
		return getCurrent();
	}

	public Page<T> setPageIndex(long pageIndex) {
		setCurrent(pageIndex);
		return this;
	}

	/**
	 * 每页显示条数，默认 10
	 * @return
	 */
	@Override
	public long getPageSize() {
		return getSize();
	}

	public Page<T> setPageSize(long pageSize) {
		setSize(pageSize);
		return this;
	}

	/**
	 * 当前分页总页数
	 * @return
	 */
	@Override
	public long getPageCount() {
		return getPages();
	}

	public Page<T> setPageCount(long pageCount) {
		setPages(pageCount);
		return this;
	}
}
