package com.xiongyayun.athena.user.model;

import com.xiongyayun.athena.db.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Menu
 *
 * @author: Yayun.Xiong
 * @date: 2019-05-30
 */
@Data
@Entity
@Table(name = "`menu`")
public class Menu extends BaseEntity {
	private static final long serialVersionUID = 1364716093384233712L;

	/**
	 * 菜单主键ID
	 */
	@Id
	@Column(name = "`id`")
	private String id;

	/**
	 * 应用编码
	 */
	@Column(name = "`application_code`")
	private String applicationCode;

	/**
	 * 菜单编码
	 */
	@Column(name = "`code`")
	private String code;

	/**
	 * 菜单名称
	 */
	@Column(name = "`name`")
	private String name;

	/**
	 * 菜单标题
	 */
	@Column(name = "`title`")
	private String title;

	/**
	 * 菜单URL(内部相对链接，外部绝对链接)
	 */
	@Column(name = "`url`")
	private String url;

	/**
	 * 规定在何处打开链接文档
	 */
	@Column(name = "`target`")
	private String target;

	/**
	 * 菜单图标
	 */
	@Column(name = "`icon`")
	private String icon;

	/**
	 * 菜单排序
	 */
	@Column(name = "`sort`")
	private Integer sort;

	/**
	 * 菜单描述
	 */
	@Column(name = "`describe`")
	private String describe;

	/**
	 * 徽标数，展示的数字
	 */
	@Column(name = "`badge`")
	private Integer badge;

	/**
	 * 徽标数，显示小红点
	 */
	@Column(name = "`badge_dot`")
	private Boolean badgeDot;

	/**
	 * 徽标数，颜色
	 */
	@Column(name = "`badge_status`")
	private String badgeStatus;

	/**
	 * 菜单是否显示
	 */
	@Column(name = "`visible`")
	private Boolean visible;

	/**
	 * 菜单是否禁用
	 */
	@Column(name = "`disabled`")
	private Boolean disabled;

	/**
	 * 菜单是否被选中
	 */
	@Column(name = "`selected`")
	private Boolean selected;

	/**
	 * 菜单是否删除
	 */
	@Column(name = "`deleted`")
	private Boolean deleted;

	/**
	 * 菜单是否允许更新
	 */
	@Column(name = "`allow_update`")
	private Boolean allowUpdate;

	/**
	 * 菜单是否允许删除
	 */
	@Column(name = "`allow_delete`")
	private Boolean allowDelete;

	/**
	 * 是否叶节点
	 */
	@Column(name = "`leaf`")
	private Boolean leaf;

	/**
	 * 菜单父节点编码
	 */
	@Column(name = "`parent_id`")
	private String parentId;

}
