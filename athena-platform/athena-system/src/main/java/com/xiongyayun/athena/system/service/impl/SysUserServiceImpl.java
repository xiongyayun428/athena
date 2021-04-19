package com.xiongyayun.athena.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiongyayun.athena.core.exception.AthenaRuntimeException;
import com.xiongyayun.athena.system.dto.SysUserDTO;
import com.xiongyayun.athena.system.mapper.SysUserMapper;
import com.xiongyayun.athena.system.model.SysUser;
import com.xiongyayun.athena.system.service.SysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * 用户服务实现类
 *
 * @author Yayun.Xiong
 * @date 2019-05-19
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

	@Override
	public boolean save(SysUser user) {
		SysUser existEntity = this.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, user.getUsername()));
		if (existEntity != null) {
			throw new AthenaRuntimeException("用户名已存在");
		}
		return super.save(user);
	}

	@Override
	public boolean updateById(SysUser entity) {
		SysUser existEntity = this.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUserId, entity.getUserId()));
		if (existEntity == null) {
			throw new AthenaRuntimeException("用户不存在");
		}
		return super.updateById(entity);
	}

	@Override
	public boolean create(SysUserDTO dto) {
		SysUser user = new SysUser();
		BeanUtils.copyProperties(dto, user);
		return this.save(user);
	}

	@Override
	public boolean update(SysUserDTO dto) {
		SysUser user = new SysUser();
		BeanUtils.copyProperties(dto, user);
		return this.updateById(user);
	}
}
