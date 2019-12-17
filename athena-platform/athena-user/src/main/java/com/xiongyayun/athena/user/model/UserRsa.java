package com.xiongyayun.athena.user.model;

import com.xiongyayun.athena.db.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * UserRsa
 *
 * @author: Yayun.Xiong
 * @date: 2019-06-16
 */
@Data
@Entity
@Table(name = "`user_rsa`")
public class UserRsa extends BaseEntity {
	private static final long serialVersionUID = 3750325305844163127L;

	/**
	 * 主键ID
	 */
	@Id
	@Column(name = "`id`")
	private String id;

	/**
	 * 用户ID
	 */
	@Column(name = "`user_id`")
	private String userId;

	/**
	 * 公钥
	 */
	@Column(name = "`public_key`")
	private String publicKey;

	/**
	 * 私钥
	 */
	@Column(name = "`private_key`")
	private String privateKey;

	/**
	 * 算法
	 */
	@Column(name = "`algorithm`")
	private String algorithm;

}
