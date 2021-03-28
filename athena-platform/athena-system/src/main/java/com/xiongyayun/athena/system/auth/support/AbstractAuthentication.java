package com.xiongyayun.athena.system.auth.support;

import com.xiongyayun.athena.system.auth.Authentication;
import com.xiongyayun.athena.system.auth.AuthorityBridge;

/**
 * AbstractAuthentication
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/3/15
 */
public abstract class AbstractAuthentication implements Authentication {
	private AuthorityBridge[] authorities;

	private boolean authenticated = false;

	private transient String[] role;

	@Override
	public String[] getRole() {
//		if (this.role == null) {
//			this.role = PrivilegeManager.getRoleFromAuthentication(this);
//		}
		return this.role;
	}

	@Override
	public void setAuthorities(AuthorityBridge[] paramArrayOfAuthorityBridge) {
		this.authorities = paramArrayOfAuthorityBridge;
	}

	@Override
	public AuthorityBridge[] getAuthorities() {
		if (this.authorities == null) {
			return new AuthorityBridge[0];
		}
		AuthorityBridge[] authorityBridges = new AuthorityBridge[this.authorities.length];
		System.arraycopy(this.authorities, 0, authorityBridges, 0, this.authorities.length);
		return authorityBridges;
	}

	@Override
	public boolean isAuthenticated() {
		return this.authenticated;
	}

	@Override
	public void setAuthenticated(boolean paramBoolean) {
		this.authenticated = paramBoolean;
	}
}
