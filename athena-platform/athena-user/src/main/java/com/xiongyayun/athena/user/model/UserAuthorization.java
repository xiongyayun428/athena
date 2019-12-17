package com.xiongyayun.athena.user.model;

import com.xiongyayun.athena.db.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * UserAuthorization
 *
 * @author: Yayun.Xiong
 * @date: 2019-06-16
 */
@Data
@Entity
@Table(name = "`user_authorization`")
public class UserAuthorization extends BaseEntity {
	private static final long serialVersionUID = 3133703969408029820L;

	/**
	 * 用户授权主键ID
	 */
	@Id
	@Column(name = "`id`")
	private String id;

	/**
	 * 用户主键ID
	 */
	@Column(name = "`user_id`")
	private String userId;

	/**
	 * 登录类型 (手机号/邮箱/用户名) 或第三方应用名称 (微信 , 微博等)
	 */
	@Column(name = "`identity_type`")
	private String identityType;

	/**
	 * 标识 (手机号/邮箱/用户名或第三方应用的唯一标识)
	 */
	@Column(name = "`identifier`")
	private String identifier;

	/**
	 * 密码凭证 (站内的保存密码 , 站外的不保存或保存 token)
	 */
	@Column(name = "`credential`")
	private String credential;

}
