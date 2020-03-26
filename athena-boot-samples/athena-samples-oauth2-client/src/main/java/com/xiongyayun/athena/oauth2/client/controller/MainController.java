package com.xiongyayun.athena.oauth2.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * MainController
 *
 * @author: Yayun.Xiong
 * @date: 2019-08-08
 */
@RestController
public class MainController {
	@Autowired
	private ClientRegistrationRepository clientRegistrationRepository;
	@Autowired
	private OAuth2AuthorizedClientService authorizedClientService;

	@RequestMapping("/")
	public String index() {
		ClientRegistration googleRegistration =
				this.clientRegistrationRepository.findByRegistrationId("github");
		System.out.println("首页");
		return "登录成功";
	}

//	@RequestMapping("/login")
//	public String login() {
//		ClientRegistration googleRegistration =
//				this.clientRegistrationRepository.findByRegistrationId("github");
//		System.out.println("登录页面");
//		return "登录页面";
//	}

	@RequestMapping("/userinfo")
	public Object userinfo(OAuth2AuthenticationToken authentication, OAuth2AuthenticationToken principal) {
		// authentication.getAuthorizedClientRegistrationId() returns the
		// registrationId of the Client that was authorized during the Login flow
		OAuth2AuthorizedClient authorizedClient =
				this.authorizedClientService.loadAuthorizedClient(
						authentication.getAuthorizedClientRegistrationId(),
						authentication.getName());
		OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
		OAuth2RefreshToken refreshToken = authorizedClient.getRefreshToken();

		System.out.println(principal.isAuthenticated());
		System.out.println(principal.getPrincipal().getAttributes());
		System.out.println(accessToken);
		System.out.println(refreshToken);
		System.out.println(authorizedClient.getPrincipalName());
		System.out.println(authorizedClient.getClientRegistration().getProviderDetails());

		return accessToken;
	}
}
