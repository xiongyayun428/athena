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
	private Long createBy;
	/** 创建时间，在这个实体被insert的时候，会设置值 */
	@ApiModelProperty("创建时间")
	@TableField("create_time")
	private Date createTime;
	/** 最后修改人，在这个实体被update/delete的时候，会设置值 */
	@ApiModelProperty("最后修改人")
	@TableField("update_by")
	private Long updateBy;
	/** 最后修改时间，在这个实体被update/delete的时候，会设置值 */
	@ApiModelProperty("最后修改时间")
	@TableField("update_time")
	private Date updateTime;

	/**
	 * 创建人
	 * @return	创建人ID
	 */
	public Long getCreateBy() {
		return createBy;
	}

	/**
	 * 创建人
	 * @param createBy	创建人ID
	 */
	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
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
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 最后修改人
	 * @return	最后修改人ID
	 */
	public Long getUpdateBy() {
		return updateBy;
	}

	/**
	 * 最后修改人
	 * @param updateBy	最后修改人ID
	 */
	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}

	/**
	 * 最后修改时间
	 * @return	最后修改时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * 最后修改时间
	 * @param updateTime	最后修改时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
