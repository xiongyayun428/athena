package com.xiongyayun.athena.system.modules.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xiongyayun.athena.system.modules.user.mapper.SysPasswordPolicyMapper;
import com.xiongyayun.athena.system.modules.user.entity.SysPasswordPolicy;
import com.xiongyayun.athena.system.modules.user.service.SysAuthorizationService;
import com.xiongyayun.athena.system.modules.dict.service.SysDictService;
import com.xiongyayun.athena.system.modules.user.service.SysUserService;
import com.xiongyayun.athena.system.modules.user.vo.SysLoginVO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * SysAuthorizationServiceImpl
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/3/30
 */
@Service
public class SysAuthorizationServiceImpl implements SysAuthorizationService {
	@Resource
	private SysUserService sysUserService;
	@Resource
	private SysDictService sysDictService;

	@Resource
	private SysPasswordPolicyMapper sysPasswordPolicyMapper;

	@Override
	public void login(SysLoginVO vo) {

	}

	@Override
	public boolean passwordValid(String password, String policyType) {
		SysPasswordPolicy sysPasswordPolicy = this.sysPasswordPolicyMapper.selectOne(Wrappers.<SysPasswordPolicy>lambdaQuery()
				.eq(StringUtils.hasLength(policyType), SysPasswordPolicy::getPolicyType, policyType));
		// 启用密码策略
		if (sysPasswordPolicy != null && !sysPasswordPolicy.getDisabled() && !sysPasswordPolicy.valid(password)) {
			Integer reuseLimit = sysPasswordPolicy.getReuseLimit();
			Integer maxRetryCount = sysPasswordPolicy.getExpiresWarning();
			return false;
		}
		return true;
	}
}
