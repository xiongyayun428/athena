package com.xiongyayun.athena.oauth2.client.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * OAuth2ClientController
 *
 * @author: Yayun.Xiong
 * @date: 2019-08-08
 */
@RestController
public class OAuth2ClientController {
	@Resource
	private ClientRegistrationRepository clientRegistrationRepository;

	@Resource
	private OAuth2AuthorizedClientService authorizedClientService;

//	@GetMapping("/")
//	public String index(Authentication authentication) {
//		System.out.println(authentication.getName());
////		ClientRegistration clientRegistration =
////				this.clientRegistrationRepository.findByRegistrationId("github");
////
////		OAuth2AuthorizedClient authorizedClient =
////				this.authorizedClientService.loadAuthorizedClient("github", authentication.getName());
////
////		OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
////		System.out.println(accessToken.getTokenValue());
//		return "index";
//	}

//	@GetMapping("/")
//	public String index(Model model,
//						@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
//						@AuthenticationPrincipal OAuth2User oauth2User) {
//		model.addAttribute("userName", oauth2User.getName());
//		model.addAttribute("clientName", authorizedClient.getClientRegistration().getClientName());
//		System.out.println(authorizedClient.getAccessToken());
//		System.out.println(authorizedClient.getRefreshToken());
//		model.addAttribute("userAttributes", oauth2User.getAttributes());
//		return "index";
//	}

	@GetMapping("/")
	public String index(@AuthenticationPrincipal Jwt jwt) {
		return String.format("Hello, %s!", jwt.getSubject());
	}

	@GetMapping("/message")
	public String message() {
		return "secret message";
	}
}
