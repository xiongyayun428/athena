package com.xiongyayun.athena.application.menu.controller;

import com.xiongyayun.athena.application.menu.dto.MenuDTO;
import com.xiongyayun.athena.application.menu.service.MenuService;
import com.xiongyayun.athena.core.annotation.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * MenuController
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/2/26
 */
@Validated
@CacheConfig(cacheNames = "menuCache")
@RestController
@RequestMapping("/sys/menu")
@Api(tags = {"菜单服务"})
public class MenuController {
	@Resource
	private MenuService menuService;

	@Log("根据主键查询菜单")
	@ApiOperation("根据主键查询菜单")
	@Cacheable(key = "#id")
	@GetMapping("/get/{id}")
	public MenuDTO get(@ApiParam("主键ID") @PathVariable("id") String id) {
		return null;
	}
}
