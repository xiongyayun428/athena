package com.xyy.athena.user.controller;

import com.xyy.athena.core.annotation.Logger;
import com.xyy.athena.user.model.User;
import com.xyy.athena.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

	@Logger("查询所有用户信息")
	@GetMapping("selectAll")
	public Object selectAll(@Valid User user) {
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
	@Nullable
	public void delete() {

	}


}
