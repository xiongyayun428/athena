//package com.xiongyayun.athena.spring.boot.oauth2.server.security;
//
//import com.alibaba.fastjson.annotation.JSONField;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.Collections;
//
///**
// * 自定义认证用户信息
// *
// * @author: Yayun.Xiong
// * @date: 2019-07-01
// */
//public class OAuthUser implements UserDetails {
//	/**
//	 * 用户ID
//	 */
//	private Long userId;
//	/**
//	 * 登录名
//	 */
//	private String username;
//	/**
//	 * 密码
//	 */
//	private String password;
//
//	/**
//	 * 用户权限
//	 */
//	private Collection<? extends GrantedAuthority> authorities;
//	/**
//	 * 是否已锁定
//	 */
//	private boolean accountNonLocked;
//	/**
//	 * 是否已过期
//	 */
//	private boolean accountNonExpired;
//	/**
//	 * 是否启用
//	 */
//	private boolean enabled;
//	/**
//	 * 密码是否已过期
//	 */
//	private boolean credentialsNonExpired;
//	/**
//	 * 认证客户端ID
//	 */
//	private String clientId;
//	/**
//	 * 认证中心ID,适用于区分多用户源,多认证中心
//	 */
//	private String centerId;
//
//	/**
//	 * 昵称
//	 */
//	private String nickName;
//
//	/**
//	 * 头像
//	 */
//	private String avatar;
//
//	/**
//	 * 账户Id
//	 */
//	private Long accountId;
//
//	/***
//	 * 账户类型
//	 */
//	private String accountType;
//
//
//	/**
//	 * 只是客户端模式.不包含用户信息
//	 *
//	 * @return
//	 */
//	public Boolean isClientOnly() {
//		return clientId != null && username == null;
//	}
//
//	public OAuthUser() {
//	}
//
//	public OAuthUser(String centerId, Long userId, String username, String password, Collection<Authority> authorities, boolean accountNonLocked, boolean accountNonExpired, boolean enabled, boolean credentialsNonExpired, String nickName, String avatar) {
//		this.centerId = centerId;
//		this.userId = userId;
//		this.username = username;
//		this.password = password;
//		this.authorities = authorities;
//		this.accountNonLocked = accountNonLocked;
//		this.accountNonExpired = accountNonExpired;
//		this.enabled = enabled;
//		this.credentialsNonExpired = credentialsNonExpired;
//		this.avatar = avatar;
//		this.nickName = nickName;
//	}
//
//	public OAuthUser(String centerId, Long accountId, Long userId, String username, String password, boolean accountNonLocked, boolean accountNonExpired, boolean enabled, boolean credentialsNonExpired, String nickName, String avatar, String accountType) {
//		this.centerId = centerId;
//		this.userId = userId;
//		this.username = username;
//		this.password = password;
//		this.accountNonLocked = accountNonLocked;
//		this.accountNonExpired = accountNonExpired;
//		this.enabled = enabled;
//		this.credentialsNonExpired = credentialsNonExpired;
//		this.avatar = avatar;
//		this.nickName = nickName;
//		this.accountType = accountType;
//		this.accountId = accountId;
//	}
//
//
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		if (authorities == null) {
//			return Collections.EMPTY_LIST;
//		}
//		return this.authorities;
//	}
//
//	@JsonIgnore
//	@JSONField(serialize = false)
//	@Override
//	public String getPassword() {
//		return this.password;
//	}
//
//	@Override
//	public String getUsername() {
//		return this.username;
//	}
//
//	@Override
//	public boolean isAccountNonExpired() {
//		return this.accountNonExpired;
//	}
//
//	@Override
//	public boolean isAccountNonLocked() {
//		return this.accountNonLocked;
//	}
//
//	@Override
//	public boolean isCredentialsNonExpired() {
//		return this.credentialsNonExpired;
//	}
//
//	@Override
//	public boolean isEnabled() {
//		return this.enabled;
//	}
//
//	public Long getUserId() {
//		return userId;
//	}
//
//	public void setUserId(Long userId) {
//		this.userId = userId;
//	}
//
//	public void setUsername(String username) {
//		this.username = username;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//	public void setAccountNonLocked(boolean accountNonLocked) {
//		this.accountNonLocked = accountNonLocked;
//	}
//
//	public void setAccountNonExpired(boolean accountNonExpired) {
//		this.accountNonExpired = accountNonExpired;
//	}
//
//	public void setEnabled(boolean enabled) {
//		this.enabled = enabled;
//	}
//
//	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
//		this.credentialsNonExpired = credentialsNonExpired;
//	}
//
//	public String getClientId() {
//		return clientId;
//	}
//
//	public void setClientId(String clientId) {
//		this.clientId = clientId;
//	}
//
//	public String getCenterId() {
//		return centerId;
//	}
//
//	public void setCenterId(String centerId) {
//		this.centerId = centerId;
//	}
//
//	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
//		this.authorities = authorities;
//	}
//
//
//	public String getNickName() {
//		return nickName;
//	}
//
//	public void setNickName(String nickName) {
//		this.nickName = nickName;
//	}
//
//	public String getAvatar() {
//		return avatar;
//	}
//
//	public void setAvatar(String avatar) {
//		this.avatar = avatar;
//	}
//
//	public Long getAccountId() {
//		return accountId;
//	}
//
//	public void setAccountId(Long accountId) {
//		this.accountId = accountId;
//	}
//
//	public String getAccountType() {
//		return accountType;
//	}
//
//	public void setAccountType(String accountType) {
//		this.accountType = accountType;
//	}
//}
