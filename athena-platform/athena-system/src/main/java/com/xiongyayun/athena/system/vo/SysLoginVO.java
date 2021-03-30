package com.xiongyayun.athena.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * SysLoginVO
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/3/30
 */
@Data
@ApiModel("系统登录")
public class SysLoginVO implements Serializable {
	@ApiModelProperty(value = "登录类型")
	private String identityType;
	@ApiModelProperty(value = "标识 (手机号/邮箱/用户名或第三方应用的唯一标识)")
	private String identity;
	@ApiModelProperty(value = "密码凭证")
	private String credential;
	@ApiModelProperty(value = "验证码")
	private String captcha;
}
