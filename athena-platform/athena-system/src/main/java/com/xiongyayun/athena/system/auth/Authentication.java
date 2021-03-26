package com.xiongyayun.athena.system.auth;

import java.io.Serializable;

/**
 * 鉴权
 *
 * @author Yayun.Xiong
 * @date 2019-06-16
 */
public interface Authentication extends AuthenticationBridge, Serializable {

	String[] getRole();
}
