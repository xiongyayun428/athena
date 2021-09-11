package com.xiongyayun.athena.application.user.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * UserDTO
 *
 * @author 熊亚运
 * @date 2019-05-23
 */
@Data
@Accessors(chain = true)
@ApiModel("系统用户")
public class UserDTO implements Serializable {
	private Long userId;
	private String username;
	private String realname;
}
