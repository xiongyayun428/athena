//package com.xiongyayun.athena.security.principal;
//
//import org.springframework.security.core.GrantedAuthority;
//
//import java.util.*;
//
///**
// * ClientPrincipal
// *
// * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
// * @date 2021/4/23
// */
//public class ClientPrincipal implements ClientDetails {
//
//	private Client client;
//
//	public ClientPrincipal(Client client) {
//		this.client = client;
//	}
//
//	@Override
//	public String getClientId() {
//		return client.getClientId();
//	}
//
//	@Override
//	public Set<String> getResourceIds() {
//		return new HashSet<>(Arrays.asList(client.getResourceIds().split(MessageConstant.SPLIT_COMMA)));
//	}
//
//	@Override
//	public boolean isSecretRequired() {
//		return client.getSecretRequire();
//	}
//
//	@Override
//	public String getClientSecret() {
//		return client.getClientSecret();
//	}
//
//	@Override
//	public boolean isScoped() {
//		return client.getScopeRequire();
//	}
//
//	@Override
//	public Set<String> getScope() {
//		return new HashSet<>(Arrays.asList(client.getScope().split(MessageConstant.SPLIT_COMMA)));
//	}
//
//	@Override
//	public Set<String> getAuthorizedGrantTypes() {
//		return new HashSet<>(Arrays.asList(client.getAuthorizedGrantTypes().split(MessageConstant.SPLIT_COMMA)));
//	}
//
//	@Override
//	public Set<String> getRegisteredRedirectUri() {
//		return new HashSet<>(Arrays.asList(client.getWebServerRedirectUri().split(MessageConstant.SPLIT_COMMA)));
//	}
//
//	@Override
//	public Collection<GrantedAuthority> getAuthorities() {
//		Collection<GrantedAuthority> collection = new ArrayList<>();
//		Arrays.asList(client.getAuthorities().split(MessageConstant.SPLIT_COMMA)).forEach(
//				auth -> collection.add((GrantedAuthority) () -> auth)
//		);
//		return collection;
//	}
//
//	@Override
//	public Integer getAccessTokenValiditySeconds() {
//		return client.getAccessTokenValidity();
//	}
//
//	@Override
//	public Integer getRefreshTokenValiditySeconds() {
//		return client.getRefreshTokenValidity();
//	}
//
//	@Override
//	public boolean isAutoApprove(String s) {
//		return false;
//	}
//
//	@Override
//	public Map<String, Object> getAdditionalInformation() {
//		return null;
//	}
//}
