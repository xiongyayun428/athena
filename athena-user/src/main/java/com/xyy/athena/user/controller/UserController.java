package com.xyy.athena.user.controller;

import com.xyy.athena.core.annotation.Logger;
import com.xyy.athena.user.model.User;
import com.xyy.athena.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
	@GetMapping("select")
	public Object select(@Valid User user) {
		return userService.selectAll(user);
	}

	/**
	 * 根据userId查询用户信息
	 * @param userId
	 */
	@GetMapping("select/{userId}")
	public User findUserById(@PathVariable("userId") String userId) {
	    return userService.findUserById(userId);
	}

	@Logger("新增用户信息")
	@PostMapping("add")
	public int add(@Valid User user) {
		return userService.add(user);
	}

	@Logger("修改用户信息")
	@PutMapping("update")
	public int update(@Valid User user) {
		return userService.update(user);
	}

	@Logger("删除用户信息")
	@DeleteMapping("delete/{userId}")
	public int delete(@PathVariable("userId") String userId) {
		return userService.delete(userId);
	}


}
