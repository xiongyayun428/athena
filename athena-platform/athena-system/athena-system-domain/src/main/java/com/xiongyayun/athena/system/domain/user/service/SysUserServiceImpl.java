//package com.xiongyayun.athena.system.domain.user.service;
//
//import com.baomidou.mybatisplus.core.toolkit.Wrappers;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.xiongyayun.athena.components.common.exception.AthenaRuntimeException;
//import com.xiongyayun.athena.security.principal.UserPrincipal;
//import com.xiongyayun.athena.system.core.constant.SystemConstant;
//import com.xiongyayun.athena.system.modules.user.dto.SysUserDTO;
//import com.xiongyayun.athena.system.modules.user.mapper.SysUserMapper;
//import com.xiongyayun.athena.system.modules.user.entity.SysUser;
//import com.xiongyayun.athena.system.modules.user.service.SysUserService;
//import org.springframework.beans.BeanUtils;
//import org.springframework.security.authentication.AccountExpiredException;
//import org.springframework.security.authentication.CredentialsExpiredException;
//import org.springframework.security.authentication.DisabledException;
//import org.springframework.security.authentication.LockedException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.util.ObjectUtils;
//
//import javax.annotation.Resource;
//
///**
// * 用户服务实现类
// *
// * @author Yayun.Xiong
// * @date 2019-05-19
// */
//@Service
//public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
//
//	@Resource
//	private PasswordEncoder passwordEncoder;
//
//	@Override
//	public boolean save(SysUser user) {
//		SysUser existEntity = this.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, user.getUsername()));
//		if (existEntity != null) {
//			throw new AthenaRuntimeException("用户名已存在");
//		}
//		return super.save(user);
//	}
//
//	@Override
//	public boolean updateById(SysUser entity) {
//		SysUser existEntity = this.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUserId, entity.getUserId()));
//		if (existEntity == null) {
//			throw new AthenaRuntimeException("用户不存在");
//		}
//		return super.updateById(entity);
//	}
//
//	@Override
//	public boolean create(SysUserDTO dto) {
//		SysUser user = new SysUser();
//		BeanUtils.copyProperties(dto, user);
//		return this.save(user);
//	}
//
//	@Override
//	public boolean update(SysUserDTO dto) {
//		SysUser user = new SysUser();
//		BeanUtils.copyProperties(dto, user);
//		return this.updateById(user);
//	}
//
//	@Override
//	public boolean delete(String userid) {
//		return this.removeById(userid);
//	}
//
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		SysUser sysUser = this.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, username));
//		if (ObjectUtils.isEmpty(sysUser)) {
//			throw new UsernameNotFoundException(SystemConstant.USERNAME_PASSWORD_ERROR);
//		}
//		UserPrincipal userPrincipal = new UserPrincipal();
//		userPrincipal.setUsername(username);
//		userPrincipal.setPassword(passwordEncoder.encode("admin"));
//		userPrincipal.setEnabled(true);
//
//		if (!userPrincipal.isEnabled()) {
//			throw new DisabledException(SystemConstant.ACCOUNT_DISABLED);
//		} else if (!userPrincipal.isAccountNonLocked()) {
//			throw new LockedException(SystemConstant.ACCOUNT_LOCKED);
//		} else if (!userPrincipal.isAccountNonExpired()) {
//			throw new AccountExpiredException(SystemConstant.ACCOUNT_EXPIRED);
//		} else if (!userPrincipal.isCredentialsNonExpired()) {
//			throw new CredentialsExpiredException(SystemConstant.CREDENTIALS_EXPIRED);
//		}
//		return userPrincipal;
//	}
//}
