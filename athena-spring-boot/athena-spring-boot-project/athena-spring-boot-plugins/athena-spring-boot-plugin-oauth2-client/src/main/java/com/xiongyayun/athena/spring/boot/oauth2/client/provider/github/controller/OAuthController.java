//package com.xiongyayun.athena.oauth2.github.controller;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.validation.constraints.NotBlank;
//
///**
// * OAuthController
// *
// * @author Yayun.Xiong
// * @date 2019-06-26
// */
//@Slf4j
//@Validated
//@RestController
//@RequestMapping("/login/oauth")
//public class OAuthController {
//	/**
//	 * 请求令牌
//	 * https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/#redirect-urls
//	 * GitHub会使用临时code代码参数以及您在state参数的上一步中提供的状态重定向回您的站点。
//	 * 临时代码将在10分钟后过期。如果状态不匹配，则第三方创建请求，您应该中止该过程。
//	 * @param clientId
//	 * @param code
//	 */
//	@GetMapping("github/access_token")
//	public void accessToken(@NotBlank(message = "应用程序的客户端ID不能为空") String clientId, @NotBlank(message = "授权码不能为空") String code) {
//		System.out.println(clientId);
//		System.out.println(code);
//	}
//}
