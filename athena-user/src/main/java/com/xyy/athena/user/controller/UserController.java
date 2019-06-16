package com.xyy.athena.user.controller;

import cn.hutool.crypto.asymmetric.RSA;
import com.xyy.athena.core.annotation.Logger;
import com.xyy.athena.core.exception.AthenaException;
import com.xyy.athena.core.exception.AthenaRuntimeException;
import com.xyy.athena.user.factory.AuthenticationFactory;
import com.xyy.athena.user.model.User;
import com.xyy.athena.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * User
 *
 * @author Yayun.Xiong
 * @date 2019-03-03 16:29
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	/**
	 * 单独使用RSA
	 */
	private boolean separateRSA = false;
	private static RSA rsa;

	@Logger("根据登录用户名查询公钥")
	@GetMapping("publicKey")
	public String publicKey() {
		if (!separateRSA) {
			rsa = new RSA();
			log.info(rsa.getPublicKeyBase64());
			log.info(rsa.getPrivateKeyBase64());
		} else {
			// 查询数据库
		}
		return rsa.getPublicKeyBase64();
	}

	@Logger("用户登录")
	@GetMapping("login")
	public void login(String identityType, String identifier, String credential) throws AthenaException {
		Assert.hasText(identityType, "登录类型不能为空");
		// 0. 判断登录方式
		var userAuthorization = AuthenticationFactory.get(identityType).authorization(identifier, credential);
		if (userAuthorization == null) {
			throw new AthenaRuntimeException("UserNotExist");
		}
		// 1. 查询用户信息
		var user = userService.findUserById(userAuthorization.getUserId());
		// 2. 判断用户状态是否正常
		if (user.getStatus() != 0) {
			throw new AthenaRuntimeException("UserDefinedError", new Object[] {"用户名状态不正常"});
		}
	}

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

//	@GetMapping("test")
//	@MessageMapping("/sendTest")
//	@SendTo("/topic")
//	public Object test() {
////		log.info("接收到了信息" + message.getName());
//		return "XYY 测试";
//	}
	// 接收客户端发送的订阅
//	@SubscribeMapping("/subscribeTest")
	@MessageMapping("subscribeTest")
	public Object subscribeTest() {
		log.info("XXX用户订阅了我。。。");
		return "XYY 测试";
	}

	@GetMapping("/subscribeTest")
	public void greet(String greeting) {
		String text = "[" + System.currentTimeMillis() + "]:" + greeting;
		this.messagingTemplate.convertAndSend("/topicTest/subscribeTest", text);
	}

	@GetMapping("/subscribeTest1")
	@SendTo("/topicTest/subscribeTest")
	public String greet1(String greeting) {
		String text = "[" + System.currentTimeMillis() + "]:" + greeting;
		return text;
	}

//	@SendTo("/subscribeTest")
//	@GetMapping("push")
//	public void push() {
//		log.info("主动推送数据。。。");
//		messagingTemplate.convertAndSend("/subscribeTest", "主动推送");
//	}

	@MessageMapping("hello")
//	@SendToUser(value = "/topicTest/subscribeTest", broadcast = false)
	@SendTo("/topicTest/subscribeTest")
	public String push(String message) {
		log.info("主动推送数据。。。" + message);
		return "主动推送数据" + message;
	}
}
