package com.xiongyayun.athena.system.dto;

import com.xiongyayun.athena.core.ValidationGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * UserDto
 *
 * @author Yayun.Xiong
 * @date 2020/11/28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysUserDTO implements Serializable {
	@NotBlank(groups = {ValidationGroup.Update.class}, message = "用户主键不能为空")
	private String userId;

	@NotBlank(groups = {ValidationGroup.Create.class}, message = "用户名不能为空")
	@Size(groups = {ValidationGroup.Create.class}, min = 6, max = 32, message = "用户名[${validatedValue}]长度必须在{min}和{max}之间")
	private String username;

	@NotBlank(groups = {ValidationGroup.Create.class}, message = "密码不能为空")
	@Size(groups = {ValidationGroup.Create.class}, min = 6, max = 64, message = "密码长度必须在{min}和{max}之间")
	private String password;

	private String realname;
	private String nickname;

	@NotBlank(groups = {ValidationGroup.Create.class, ValidationGroup.Update.class}, message = "用户主键不能为空")
	private String lastVisitIp;

	/**
	 * 属性必须有这两个分组的才验证(配合spring的@Validated功能分组使用)
	 */
	@GroupSequence({ValidationGroup.Create.class, ValidationGroup.Update.class})
	public interface All{};
}
