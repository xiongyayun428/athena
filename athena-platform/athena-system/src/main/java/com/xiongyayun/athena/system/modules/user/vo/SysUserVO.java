package com.xiongyayun.athena.system.modules.user.vo;

import com.xiongyayun.athena.core.ValidationGroup;
import io.swagger.annotations.ApiModel;
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
@ApiModel("系统用户")
public class SysUserVO implements Serializable {
	@NotBlank(groups = {ValidationGroup.Update.class}, message = "用户主键不能为空")
	private String userId;
	@NotBlank(message = "用户名不能为空")
	private String username;
	@NotBlank(message = "密码不能为空")
	private String password;
	private String realname;
	private String nickname;
}
