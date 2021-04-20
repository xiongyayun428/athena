package com.xiongyayun.athena.system.modules.user.service;

import com.xiongyayun.athena.system.modules.user.vo.SysLoginVO;

/**
 * SysAuthorizationService
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/3/30
 */
public interface SysAuthorizationService {
	void login(SysLoginVO vo);

	/**
	 * 检查密码是否有效
	 * @param password	待校验的密码
	 * @param policyType	密码策略类型
	 * @return
	 */
	boolean passwordValid(String password, String policyType);
}
