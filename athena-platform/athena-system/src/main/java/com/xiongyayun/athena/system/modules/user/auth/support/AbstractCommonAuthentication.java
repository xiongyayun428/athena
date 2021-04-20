package com.xiongyayun.athena.system.modules.user.auth.support;

import com.xiongyayun.athena.system.modules.user.auth.CommonAuthentication;

/**
 * AbstractCommonAuthentication
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/3/15
 */
public abstract class AbstractCommonAuthentication extends AbstractAuthentication implements CommonAuthentication {

	@Override
	public Object getPrincipal() {
		return getUserName();
	}

	@Override
	public Object getCredentials() {
		return getPassWord();
	}
}
