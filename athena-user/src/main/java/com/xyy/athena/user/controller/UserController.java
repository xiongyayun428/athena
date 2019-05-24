package com.xyy.athena.user.controller;

import com.xyy.athena.user.model.User;
import com.xyy.athena.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * User
 *
 * @author Yayun.Xiong
 * @date 2019-03-03 16:29
 */
@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping("selectAll")
	public Object selectAll(User user) {
		return userService.selectAll(user);
	}

	/**
	 * 根据userId查询用户信息
	 * @param userId
	 */
	@GetMapping("findUserById/{userId}")
	public void findUserById(@PathVariable("userId") String userId) {
	    userService.delete("111");
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
