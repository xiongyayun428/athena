package com.xiongyayun.athena.system.modules.user.auth.support;

/**
 * UsernamePasswordAuthentication
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/3/15
 */
public class UsernamePasswordAuthentication extends AbstractCommonAuthentication {

	private String userName;
	private String passWord;

	public UsernamePasswordAuthentication(String username, String password) {
		this.userName = username;
		this.passWord = password;
	}

	@Override
	public String getUserName() {
		return this.userName;
	}

	@Override
	public String getPassWord() {
		return this.passWord;
	}
}
