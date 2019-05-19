package com.xyy.athena.role.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xyy.athena.role.api.RoleApi;

/**
 * 角色
 * 允许动态刷新配置
 *
 * @author Yayun.Xiong
 * @date 2019-04-22
 */
@RefreshScope
@RestController
@RequestMapping("/role")
public class RoleController implements RoleApi {
	// 获取resources目录下面的yml文件内容
	@Value("${server.port}")
	private String port;
	
	@Value("${user.password}")
	private String name;

	@Override
	@RequestMapping("/roles")
	public String roles(@RequestParam String userId) {
//		try {
//			Thread.sleep(3000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		return port + " roles (" + userId + ")" + name;
	}

	@Override
	@RequestMapping("/get")
	public String get(String userId) {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return port + " get (" + userId + ")" + name;
	}
	
}