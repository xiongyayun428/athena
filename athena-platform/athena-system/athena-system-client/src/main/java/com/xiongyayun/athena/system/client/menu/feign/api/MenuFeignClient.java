package com.xiongyayun.athena.system.client.menu.feign.api;

import com.xiongyayun.athena.system.client.menu.dto.MenuDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 系统菜单接口
 * @author Yayun.Xiong
 * @date 2021-09-11 16:29
 */
@FeignClient("athena/system")
public interface MenuFeignClient {
	/**
	 * 新增系统菜单
	 * @param menuDTO
	 * @return
	 */
	@PostMapping("/sys/menu/create")
    MenuDTO create(@RequestBody MenuDTO menuDTO);

	/**
	 * 查询系统菜单列表
	 * @param menuDTO
	 * @return
	 */
	@PostMapping("/sys/menu/list")
	List<MenuDTO> list(@RequestBody MenuDTO menuDTO);

	/**
	 * 分页查询系统菜单列表
	 * @param menuDTO
	 * @return
	 */
	@PostMapping("/sys/menu/page")
	List<MenuDTO> page(@RequestBody MenuDTO menuDTO);
}
