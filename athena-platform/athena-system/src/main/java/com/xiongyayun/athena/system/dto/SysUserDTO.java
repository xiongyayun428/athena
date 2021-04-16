package com.xiongyayun.athena.system.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
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
	@NotBlank(message = "用户名不能为空")
	@Size(min = 6, max = 32)
	private String username;
	@NotBlank(message = "密码不能为空")
	@Size(min = 6, max = 64)
	private String password;
	private String realname;
	private String nickname;
	private String lastVisitIp;
}
