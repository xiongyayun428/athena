//package com.xiongyayun.athena.spring.boot.oauth2.server.services.impl;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.oauth2.provider.ClientDetails;
//import org.springframework.security.oauth2.provider.ClientDetailsService;
//import org.springframework.security.oauth2.provider.ClientRegistrationException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
///**
// * ClientDetailsServiceImpl
// *
// * @author: Yayun.Xiong
// * @date: 2019-07-01
// */
//@Slf4j
//@Service
//@Transactional(rollbackFor = Exception.class)
//public class ClientDetailsServiceImpl implements ClientDetailsService {
//
////	@Autowired
////	private BaseAppRemoteService baseAppRemoteService;
//
//	@Override
//	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
////		ClientDetails details = baseAppRemoteService.getAppClientInfo(clientId).getData();
////		if (details != null && details.getAdditionalInformation() != null) {
////			String status = details.getAdditionalInformation().getOrDefault("status", "0").toString();
////			if(!"1".equals(status)){
////				throw new ClientRegistrationException("客户端已被禁用");
////			}
////		}
////		return details;
////		return new OAuthClient();
//		return null;
//	}
//}
