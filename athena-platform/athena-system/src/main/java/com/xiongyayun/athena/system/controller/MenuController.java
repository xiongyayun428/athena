package com.xiongyayun.athena.system.controller;

import com.xiongyayun.athena.core.annotation.Log;
import com.xiongyayun.athena.system.mapper.MenuMapper;
import com.xiongyayun.athena.system.model.Menu;
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
@RequestMapping("/menu")
@Api(tags = {"菜单服务"})
public class MenuController {
	@Resource
	private MenuMapper menuMapper;

	@Log("根据主键查询菜单")
	@ApiOperation("根据主键查询菜单")
	@Cacheable(key = "#id")
	@GetMapping("/get/{id}")
	public Menu get(@ApiParam("主键ID") @PathVariable("id") String id) {
		return menuMapper.selectById(id);
	}
}