package com.xiongyayun.athena.user.model;

import com.xiongyayun.athena.core.model.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * User
 *
 * @author: Yayun.Xiong
 * @date: 2019-06-05
 */
@Data
@Entity
@Table(name = "`user`")
public class User extends BaseEntity {
	private static final long serialVersionUID = 8047275337956610971L;

	/**
	 * 用户主键ID
	 */
	@Id
	@Column(name = "`user_id`")
	private String userId;

	/**
	 * 用户名
	 */
	@NotBlank(message = "用户名不能为空")
	@Size(min = 2, max = 20, message = "用户名长度必须为2-20个字符")
	@Column(name = "`user_name`", nullable = false, length = 20, unique = true)
	private String userName;

	/**
	 * 真实姓名
	 */
	@Column(name = "`real_name`")
	private String realName;

	/**
	 * 昵称
	 */
	@Column(name = "`nick_name`")
	private String nickName;

	/**
	 * 限制登录的IP地址
	 */
	@Column(name = "`access_ip`")
	private String accessIp;

	/**
	 * 限制登录的MAC地址
	 */
	@Column(name = "`access_mac`")
	private String accessMac;

	/**
	 * 状态
	 */
	@Column(name = "`status`")
	private Integer status;

	/**
	 * 最近登录的IP地址
	 */
	@Column(name = "`last_visit_ip`")
	private String lastVisitIp;

	/**
	 * 最近登录的时间
	 */
	@Column(name = "`last_visit_date`")
	private Date lastVisitDate;

	/**
	 * 访问次数
	 */
	@Column(name = "`visit_count`")
	private Integer visitCount;

	/**
	 * 有效期限
	 */
	@Column(name = "`visit_date`")
	private Date visitDate;

	/**
	 * 登录密码错误次数
	 */
	@Column(name = "`error_times`")
	private Integer errorTimes;

	/**
	 * 是否允许删除(0: false，1: true)
	 */
	@Column(name = "`allow_delete`")
	private Boolean allowDelete;

}
