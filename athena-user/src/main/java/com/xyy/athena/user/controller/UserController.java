package com.xyy.athena.user.controller;

//import com.xyy.athena.core.utils.SystemUtil;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * User
 *
 * @author Yayun.Xiong
 * @date 2019-03-03 16:29
 */
@RestController
@RequestMapping("/user")
public class UserController {
//	@Autowired
//	private RestTemplate restTemplate;
//	@Autowired
//	private RoleApi roleApi;
//
//	@RequestMapping(value = "/users", method = RequestMethod.GET)
//	String users(@RequestParam String userId) {
////		String roles = restTemplate.getForObject("http://athena-role/role/roles?userId=" + userId, String.class);
//		String roles = roleApi.get(userId);
//		return "Hello World! > " + roles;
//	}
	
//	@RequestMapping("/test")
//	String test(HttpServletRequest request) {
//		return "<div>IP: " + SystemUtil.getClientIP(request) + "</div>"
//				+ "<div>MAC: " + SystemUtil.getClientMac(request) + "</div>";
//	}
//	/**
//     * LoadBalanced 注解表明restTemplate使用LoadBalancerClient执行请求
//     *
//     * @return
//     */
//	@Bean
//    @LoadBalanced
//    RestTemplate restTemplate() {
//        return new RestTemplate();
//    }

	/**
	 * 根据userId查询用户信息
	 * @param userId
	 */
	@GetMapping("findUserById/{userId}")
	public void findUserById(@PathVariable("userId") String userId) {
		System.out.println("----------findUserById/" + userId);
	}

	@PostMapping("add")
	public void add() {
		System.out.println("----------add");
	}

	@PutMapping("update")
	public void update() {

	}

	@DeleteMapping("delete")
	public void delete() {

	}
}
