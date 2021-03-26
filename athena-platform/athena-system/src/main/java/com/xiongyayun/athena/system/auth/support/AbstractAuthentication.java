package com.xiongyayun.athena.system.auth.support;

import com.xiongyayun.athena.system.auth.Authentication;
import com.xiongyayun.athena.system.auth.AuthorityBridge;
import netscape.security.PrivilegeManager;

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

	public String[] getRole() {
//		if (this.role == null) {
//			this.role = PrivilegeManager.getRoleFromAuthentication(this);
//		}
		return this.role;
	}

	public void setAuthorities(AuthorityBridge[] paramArrayOfAuthorityBridge) {
		this.authorities = paramArrayOfAuthorityBridge;
	}

	public AuthorityBridge[] getAuthorities() {
		if (this.authorities == null) {
			return new AuthorityBridge[0];
		}
		AuthorityBridge[] authorityBridges = new AuthorityBridge[this.authorities.length];
		System.arraycopy(this.authorities, 0, authorityBridges, 0, this.authorities.length);
		return authorityBridges;
	}

	public boolean isAuthenticated() {
		return this.authenticated;
	}

	public void setAuthenticated(boolean paramBoolean) {
		this.authenticated = paramBoolean;
	}
}
