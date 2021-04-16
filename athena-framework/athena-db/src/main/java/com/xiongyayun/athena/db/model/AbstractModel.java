package com.xiongyayun.athena.db.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.xiongyayun.athena.core.model.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 抽象数据库模型
 *
 * @author Yayun.Xiong
 * @date 2020/11/28
 */
//@ApiModel("抽象模型")
public abstract class AbstractModel implements Model {
	/**
	 *
	 */
	private static final long serialVersionUID = 6041192812226723245L;
	/** 创建人，在这个实体被insert的时候，会设置值 */
	@ApiModelProperty("创建人")
	@TableField("create_by")
	private String createBy;
	/** 创建时间，在这个实体被insert的时候，会设置值 */
	@ApiModelProperty("创建时间")
	@TableField("create_time")
	private Date createTime;
	/** 最后更新用户，在这个实体被update/delete的时候，会设置值 */
	@ApiModelProperty("最后更新用户")
	@TableField("last_update_by")
	private String lastUpdateBy;
	/** 最后更新时间，在这个实体被update/delete的时候，会设置值 */
	@ApiModelProperty("最后更新时间")
	@TableField("last_update_time")
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
	public AbstractModel setCreateBy(String createBy) {
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
	public AbstractModel setCreateTime(Date createTime) {
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
	public AbstractModel setLastUpdateBy(String lastUpdateBy) {
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
	public AbstractModel setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
		return this;
	}
}
