package com.xiongyayun.athena.core.entity;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 数据库实体类
 *
 * @author Yayun.Xiong
 * @date 2020/11/28
 */
public class BaseEntity implements Entity {
	/**
	 *
	 */
	private static final long serialVersionUID = -8778254915383584318L;

	/** 创建人，在这个实体被insert的时候，会设置值 */
	@ApiModelProperty("创建人")
//	@TableField(value = "create_by", fill = FieldFill.INSERT)
	private String createBy;
	/** 创建时间，在这个实体被insert的时候，会设置值 */
	@ApiModelProperty("创建时间")
//	@TableField(value = "create_time", fill = FieldFill.INSERT)
	private Date createTime;
	/** 最后更新用户，在这个实体被update/delete的时候，会设置值 */
	@ApiModelProperty("最后更新用户")
//	@TableField(value = "last_update_by", fill = FieldFill.UPDATE)
	private String lastUpdateBy;
	/** 最后更新时间，在这个实体被update/delete的时候，会设置值 */
	@ApiModelProperty("最后更新时间")
//	@TableField(value = "last_update_time", fill = FieldFill.UPDATE)
	private Date lastUpdateTime;

	/**
	 * 创建人
	 * @return	创建人ID
	 */
	public String getCreateBy() {
		return createBy;
	}

	/**
	 * 创建人
	 * @param createBy	创建人ID
	 */
	public BaseEntity setCreateBy(String createBy) {
		this.createBy = createBy;
		return this;
	}

	/**
	 * 创建时间
	 * @return	创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 创建时间
	 * @param createTime	创建时间
	 */
	public BaseEntity setCreateTime(Date createTime) {
		this.createTime = createTime;
		return this;
	}

	/**
	 * 最后更新用户
	 * @return	最后更新用户
	 */
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}

	/**
	 * 最后更新用户
	 * @param lastUpdateBy	最后更新用户
	 */
	public BaseEntity setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
		return this;
	}

	/**
	 * 最后更新时间
	 * @return	最后更新时间
	 */
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * 最后更新时间
	 * @param lastUpdateTime	最后更新时间
	 */
	public BaseEntity setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
		return this;
	}
}
