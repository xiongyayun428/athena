package com.xiongyayun.athena.system.vo.user;

import com.xiongyayun.athena.system.vo.dict.Grpup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * SysUserAddVO
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/4/15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysUserAddVO implements Serializable {
	@NotBlank(message = "用户名不能为空")
	private String username;
	@NotBlank(message = "密码不能为空")
	private String password;
	private String realname;
	private String nickname;
}
