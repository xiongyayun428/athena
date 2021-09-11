package com.xiongyayun.athena.system.modules.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiongyayun.athena.core.validation.ValidationGroup;
import com.xiongyayun.athena.security.service.UserService;
import com.xiongyayun.athena.system.modules.user.dto.SysUserDTO;
import com.xiongyayun.athena.system.modules.user.entity.SysUser;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * 系统用户服务
 *
 * @author Yayun.Xiong
 * @date 2019-05-19
 */
@Validated
public interface SysUserService extends IService<SysUser>, UserService {

	/**
	 * 创建用户
	 * @param dto
	 * @return
	 */
	@Validated({ValidationGroup.Create.class})
	boolean create(@Valid SysUserDTO dto);

	/**
	 * 更新用户
	 * @param dto
	 * @return
	 */
	@Validated({ValidationGroup.Update.class})
	boolean update(@Valid SysUserDTO dto);

	/**
	 * 删除用户
	 * @param userid
	 * @return
	 */
	boolean delete(String userid);
}
