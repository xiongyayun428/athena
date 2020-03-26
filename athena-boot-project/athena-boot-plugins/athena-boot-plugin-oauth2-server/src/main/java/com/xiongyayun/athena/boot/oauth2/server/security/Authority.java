package com.xiongyayun.athena.boot.oauth2.server.security;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.Date;

/**
 * Authority
 *
 * @author: Yayun.Xiong
 * @date: 2019-07-01
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Authority implements GrantedAuthority {
	private static final long serialVersionUID = -4682269495406460314L;

	/**
	 * 权限Id
	 */
	private String authorityId;
	/**
	 * 权限标识
	 */
	private String authority;
	/**
	 * 过期时间,用于判断权限是否已过期
	 */
	private Date expireTime;

	/**
	 * 权限所有者
	 */
	private String owner;

	@JsonProperty("isExpired")
	public Boolean getIsExpired() {
		if (expireTime != null && System.currentTimeMillis() > expireTime.getTime()) {
			return true;
		}
		return false;
	}

	public Authority() {
	}

	public Authority(String authority) {
		Assert.hasText(authority, "A granted authority textual representation is required");
		this.authority = authority;
	}

	public Authority(String authority, Date expireTime) {
		Assert.hasText(authority, "A granted authority textual representation is required");
		this.authority = authority;
		this.expireTime = expireTime;
	}

	public Authority(String authorityId, String authority, Date expireTime, String owner) {
		this.authorityId = authorityId;
		this.authority = authority;
		this.expireTime = expireTime;
		this.owner = owner;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	@Override
	public String getAuthority() {
		return this.authority;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else {
			return obj instanceof Authority ? this.authority.equals(((Authority) obj).authority) : false;
		}
	}

	@Override
	public int hashCode() {
		return this.authority.hashCode();
	}

	@Override
	public String toString() {
		return this.authority;
	}

	public String getAuthorityId() {
		return authorityId;
	}

	public void setAuthorityId(String authorityId) {
		this.authorityId = authorityId;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

}
