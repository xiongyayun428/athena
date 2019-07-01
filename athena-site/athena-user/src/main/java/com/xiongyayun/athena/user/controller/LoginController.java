package com.xiongyayun.athena.user.controller;

import com.xiongyayun.athena.core.annotation.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 * LoginController
 *
 * @author: Yayun.Xiong
 * @date: 2019-06-26
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/login/oauth")
public class LoginController {
	@Logger("根据登录用户名查询公钥")
	@GetMapping("github/access_token")
	public void accessToken(@NotBlank(message = "应用程序的客户端ID不能为空") String clientId, @NotBlank(message = "授权代码不能为空") String code) {
		// 1.获取access_token
		// 2.获取用户信息
		// 3.用户是否有绑定，未绑定引导至绑定页
		// 4.已经绑定就登录，返回access_token
	}
}
