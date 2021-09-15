package com.xiongyayun.athena.core.pagination;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 分页
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2020/8/6
 */
@ApiModel("分页")
public interface IPage<T> extends Serializable {

	/**
	 * 当前页数
	 * @return	当前页数
	 */
	@ApiModelProperty("当前页数")
	long getPageIndex();

	/**
	 * 每页条数
	 * @return	每页条数
	 */
	@ApiModelProperty("每页条数")
	long getPageSize();

	/**
	 * 总页数
	 * @return	总页数
	 */
	@ApiModelProperty("总页数")
	long getPageCount();

	/**
	 * 总记录数
	 * @return	总记录数
	 */
	@ApiModelProperty("数据总数")
	long getTotal();

	/**
	 * 当前分页查询数据列表
	 * @return	当前分页查询数据列表
	 */
	@ApiModelProperty("分页数据")
	List<T> getRecords();

	/**
	 * 是否存在上一页
	 * @return 是否存在上一页
	 */
	@ApiModelProperty("是否存在上一页")
	default boolean isHasPrevious() {
		return getPageIndex() > 1;
	}

	/**
	 * 是否存在下一页
	 * @return 是否存在下一页
	 */
	@ApiModelProperty("是否存在下一页")
	default boolean isHasNext() {
		return getPageIndex() < getPageCount();
	}


//	List<OrderItem> getSort();
}
