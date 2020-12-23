package com.xiongyayun.athena.user.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiongyayun.athena.db.model.BaseModel;
import lombok.Data;

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
@TableName("`user`")
public class User extends BaseModel {
	private static final long serialVersionUID = 8047275337956610971L;

	/**
	 * 用户主键ID
	 */
	@TableId(value = "`user_id`", type = IdType.ASSIGN_ID)
	private Long userId;

	/**
	 * 用户名
	 */
	@NotBlank(message = "用户名不能为空")
	@Size(min = 2, max = 20, message = "用户名长度必须为2-20个字符")
	@TableField(value = "`user_name`")
	private String userName;

	/**
	 * 真实姓名
	 */
	@TableField(value = "`real_name`")
	private String realName;

	/**
	 * 昵称
	 */
	@TableField(value = "`nick_name`")
	private String nickName;

	/**
	 * 限制登录的IP地址
	 */
	@TableField(value = "`access_ip`")
	private String accessIp;

	/**
	 * 限制登录的MAC地址
	 */
	@TableField(value = "`access_mac`")
	private String accessMac;

	/**
	 * 状态
	 */
	@TableField(value = "`status`")
	private Integer status;

	/**
	 * 最近登录的IP地址
	 */
	@TableField(value = "`last_visit_ip`")
	private String lastVisitIp;

	/**
	 * 最近登录的时间
	 */
	@TableField(value = "`last_visit_date`")
	private Date lastVisitDate;

	/**
	 * 访问次数
	 */
	@TableField(value = "`visit_count`")
	private Integer visitCount;

	/**
	 * 有效期限
	 */
	@TableField(value = "`visit_date`")
	private Date visitDate;

	/**
	 * 登录密码错误次数
	 */
	@TableField(value = "`error_times`")
	private Integer errorTimes;

	/**
	 * 是否允许删除(0: false，1: true)
	 */
	@TableField(value = "`allow_delete`")
	private Boolean allowDelete;

}
