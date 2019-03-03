package com.xyy.athena.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xyy.athena.core.utils.SystemUtil;
import com.xyy.athena.role.api.RoleApi;

/**
 * User
 *
 * @author Yayun.Xiong
 * @date 2019-03-03 16:29
 */
@RestController
@RequestMapping("/user")
public class User {
//	@Autowired
//	private RestTemplate restTemplate;
	@Autowired
	private RoleApi roleApi;
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	String users(@RequestParam String userId) {
//		String roles = restTemplate.getForObject("http://athena-role/role/roles?userId=" + userId, String.class);
		String roles = roleApi.get(userId);
		return "Hello World! > " + roles;
	}
	
	@RequestMapping("/test")
	String test(HttpServletRequest request) {
		return "<div>IP: " + SystemUtil.getClientIP(request) + "</div>"
				+ "<div>MAC: " + SystemUtil.getClientMac(request) + "</div>";
	}
	/**
     * LoadBalanced 注解表明restTemplate使用LoadBalancerClient执行请求
     *
     * @return
     */
//	@Bean
//    @LoadBalanced
//    RestTemplate restTemplate() {
//        return new RestTemplate();
//    }
}
