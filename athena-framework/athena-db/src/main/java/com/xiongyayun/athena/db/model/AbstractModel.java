package com.xiongyayun.athena.db.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.xiongyayun.athena.core.model.Model;

import java.util.Date;

/**
 * 抽象数据库模型
 *
 * @author Yayun.Xiong
 * @date 2020/11/28
 */
public abstract class AbstractModel implements Model {
	/** 创建人，在这个实体被insert的时候，会设置值 */
	@TableField("created_by")
	private Long createdBy;
	/** 创建时间，在这个实体被insert的时候，会设置值 */
	@TableField("created_date")
	private Date createdDate;
	/** 最后修改人，在这个实体被update/delete的时候，会设置值 */
	@TableField("last_modified_by")
	private Long lastModifiedBy;
	/** 最后修改时间，在这个实体被update/delete的时候，会设置值 */
	@TableField("last_modified_date")
	private Date lastModifiedDate;

	/**
	 * 创建人
	 * @return	创建人ID
	 */
	public Long getCreatedBy() {
		return createdBy;
	}

	/**
	 * 创建人
	 * @param createdBy	创建人ID
	 */
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * 创建时间
	 * @return	创建时间
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * 创建时间
	 * @param createdDate	创建时间
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * 最后修改人
	 * @return	最后修改人ID
	 */
	public Long getLastModifiedBy() {
		return lastModifiedBy;
	}

	/**
	 * 最后修改人
	 * @param lastModifiedBy	最后修改人ID
	 */
	public void setLastModifiedBy(Long lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	/**
	 * 最后修改时间
	 * @return	最后修改时间
	 */
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	/**
	 * 最后修改时间
	 * @param lastModifiedDate	最后修改时间
	 */
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
}
