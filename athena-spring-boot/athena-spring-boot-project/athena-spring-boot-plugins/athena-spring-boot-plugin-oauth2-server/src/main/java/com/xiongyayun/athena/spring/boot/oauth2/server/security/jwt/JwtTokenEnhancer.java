//package com.xiongyayun.athena.spring.boot.oauth2.server.security.jwt;
//
//import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.security.oauth2.provider.OAuth2Authentication;
//import org.springframework.security.oauth2.provider.token.TokenEnhancer;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * JwtTokenEnhancer
// *
// * @author Yayun.Xiong
// * @date 2019-07-05
// */
//public class JwtTokenEnhancer implements TokenEnhancer {
//	@Override
//	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
//		Map<String, Object> info = new HashMap<>();
//		info.put("blog", "https://longfeizheng.github.io/");
//		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
//		return accessToken;
//	}
//}
