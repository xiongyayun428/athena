package com.xiongyayun.athena.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

/**
 * OAuth2Controller
 *
 * @author Yayun.Xiong
 * @date 2019-08-06
 */
public class OAuth2Controller {
	@Autowired
	private OAuth2AuthorizedClientService authorizedClientService;

	private OAuth2AuthorizedClient getAuthorizedClient(OAuth2AuthenticationToken authentication) {
		return this.authorizedClientService.loadAuthorizedClient(
				authentication.getAuthorizedClientRegistrationId(), authentication.getName());
	}
}
