package com.xiongyayun.athena.oauth2.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.web.bind.annotation.RestController;

/**
 * OAuth2ClientController
 *
 * @author: Yayun.Xiong
 * @date: 2019-08-08
 */
@RestController
public class OAuth2ClientController {
	@Autowired
	private ClientRegistrationRepository clientRegistrationRepository;
}
