package com.xiongyayun.athena.system.modules.user.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xiongyayun.athena.db.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * User
 *
 * @author Yayun.Xiong
 * @date 2019-06-05
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
public class SysUser extends BaseModel {
	private static final long serialVersionUID = 8047275337956610971L;

	/**
	 * 用户主键ID
	 */
	@TableId(value = "user_id", type = IdType.ASSIGN_ID)
	private String userId;

	/**
	 * 用户名
	 */
	@NotBlank(message = "用户名不能为空")
	@Size(min = 2, max = 20, message = "用户名长度必须为2-20个字符")
	@TableField(value = "username")
	private String username;

	/**
	 * 真实姓名
	 */
	@TableField(value = "realname")
	private String realname;

	/**
	 * 昵称
	 */
	@TableField(value = "nickname")
	private String nickname;

	/**
	 * md5密码盐
	 */
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String salt;

	/**
	 * 限制登录的IP地址
	 */
	@TableField(value = "access_ip")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String accessIp;

	/**
	 * 限制登录的MAC地址
	 */
	@TableField(value = "access_mac")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String accessMac;

	/**
	 * 状态
	 */
	@TableField(value = "`status`")
	private Integer status;

	/**
	 * 最近访问的IP地址
	 */
	@TableField(value = "last_visit_ip")
	private String lastVisitIp;

	/**
	 * 最近访问的时间
	 */
	@TableField(value = "last_visit_date")
	private Date lastVisitDate;

	/**
	 * 访问次数
	 */
	@TableField(value = "visit_count")
	private Integer visitCount;

	/**
	 * 有效期限
	 */
	@TableField(value = "visit_date")
	private Date visitDate;

	/**
	 * 登录密码错误次数
	 */
	@TableField(value = "error_times")
	private Integer errorTimes;

	/**
	 * 是否允许删除(0: false，1: true)
	 */
	@TableField(value = "allow_delete")
	private Boolean allowDelete;

}
