package com.xiongyayun.athena.system.starter.auth;
/**
 * AuthenticationBridge
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/3/15
 */
public interface AuthenticationBridge {
	/**
	 * 设置认证信息里面的角色信息
	 * @param authorities	角色集合
	 */
	void setAuthorities(AuthorityBridge[] authorities);

	/**
	 * 获取认证信息里面的角色信息结合
	 * @return	角色信息集合
	 */
	AuthorityBridge[] getAuthorities();

	/**
	 * 获取密码
	 * @return	登录密码
	 */
	Object getCredentials();

	/**
	 * 获取用户名
	 * @return	用户名
	 */
	Object getPrincipal();

	/**
	 * 判断是否已经认证通过
	 * @return	认证通过则返回true，否则返回false
	 */
	boolean isAuthenticated();

	/**
	 * 设置认证通过与否
	 * @param isAuthenticated	true表示认证通过，false表示认证不通过
	 */
	void setAuthenticated(boolean isAuthenticated);
}
