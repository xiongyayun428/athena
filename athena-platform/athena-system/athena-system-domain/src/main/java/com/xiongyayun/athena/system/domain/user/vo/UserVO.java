package com.xiongyayun.athena.system.domain.user.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * UserVO
 *
 * @author Yayun.Xiong
 * @date 2021/9/11
 */
@Data
@Accessors(chain = true)
@ApiModel("系统用户")
public class UserVO implements Serializable {
	private Long userId;
	private String username;
	private String realname;
}
