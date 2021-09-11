package com.xiongyayun.athena.application.user.service;

import com.xiongyayun.athena.application.user.dto.UserDTO;
import com.xiongyayun.athena.system.domain.user.repo.UserRepo;
import com.xiongyayun.athena.system.domain.user.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 系统用户服务
 *
 * @author Yayun.Xiong
 * @date 2019-05-19
 */
@Service
public class SysUserService {
	@Resource
	private UserRepo userRepo;

	public UserDTO getById(Long userId) {
		UserVO user = userRepo.getById(userId);
		UserDTO dto = new UserDTO();
		BeanUtils.copyProperties(user, dto);
		return dto;
	}
}
