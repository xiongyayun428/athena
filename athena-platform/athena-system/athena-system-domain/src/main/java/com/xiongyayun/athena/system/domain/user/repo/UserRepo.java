package com.xiongyayun.athena.system.domain.user.repo;

import com.xiongyayun.athena.system.dao.user.mapper.SysUserMapper;
import com.xiongyayun.athena.system.dao.user.po.SysUser;
import com.xiongyayun.athena.system.domain.user.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * UserRepo
 *
 * @author Yayun.Xiong
 * @date 2021/9/11
 */
@Component
public class UserRepo {
	@Resource
	private SysUserMapper sysUserMapper;

	public UserVO getById(Serializable id) {
		SysUser user = sysUserMapper.selectById(id);
		UserVO vo = new UserVO();
		BeanUtils.copyProperties(user, vo);
		return vo;
	}
}
