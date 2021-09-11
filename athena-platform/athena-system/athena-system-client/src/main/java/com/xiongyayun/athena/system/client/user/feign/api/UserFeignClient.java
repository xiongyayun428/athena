package com.xiongyayun.athena.system.client.user.feign.api;

import com.xiongyayun.athena.system.client.user.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 系统用户接口
 * @author Yayun.Xiong
 * @date 2021-09-11 16:29
 */
@FeignClient("athena/system")
public interface UserFeignClient {
	/**
	 * 新增系统用户
	 * @param userDTO
	 * @return
	 */
	@PostMapping("/sys/user/create")
	UserDTO create(@RequestBody UserDTO userDTO);

	/**
	 * 查询用户列表
	 * @param userDTO
	 * @return
	 */
	@PostMapping("/sys/user/list")
	List<UserDTO> list(@RequestBody UserDTO userDTO);

	/**
	 * 分页查询用户列表
	 * @param userDTO
	 * @return
	 */
	@PostMapping("/sys/user/page")
	List<UserDTO> page(@RequestBody UserDTO userDTO);
}
