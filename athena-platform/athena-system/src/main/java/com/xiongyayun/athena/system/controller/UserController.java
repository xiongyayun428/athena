package com.xiongyayun.athena.system.controller;

import com.xiongyayun.athena.core.annotation.Logger;
import com.xiongyayun.athena.system.model.User;
import com.xiongyayun.athena.system.service.UserService;
import com.xiongyayun.athena.system.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * User
 *
 * @author Yayun.Xiong
 * @date 2019-03-03 16:29
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/user")
public class UserController {
	@Resource
	private UserService userService;

	@Logger("创建用户")
	@PostMapping("create")
	public Long create(@RequestBody UserVO vo) {
		// TODO vo校验
		User user = new User();
		BeanUtils.copyProperties(vo, user);
		this.userService.create(user);
		return user.getUserId();
	}

	@Logger("删除用户")
	@GetMapping("delete")
	public void delete(@RequestParam("userid") Long userId) {
		this.userService.deleteById(userId);
	}

	@Logger("获取用户详情")
	@GetMapping("get")
	public void get(@RequestParam("userid") Long userId) {
		this.userService.selectById(userId);
	}


	@Logger("更新用户详情")
	@PostMapping("update")
	public void update(@RequestBody UserVO vo) {
		// TODO vo校验
		User user = new User();
		BeanUtils.copyProperties(vo, user);
		this.userService.updateById(user);
	}

//	@Autowired
//	private UserRsaService userRsaService;
//	@Autowired
//	private UserAuthorizationService userAuthorizationService;
//	@Autowired
//	private SimpMessagingTemplate messagingTemplate;
////	@Autowired
////	private StringRedisTemplate stringRedisTemplate;
////	@Autowired
////	private RedisTemplate<String, Object> redisTemplate;
//
//	@Logger("根据登录用户名查询公钥")
//	@GetMapping("publicKey")
//	public String publicKey(@NotBlank(message = "登录类型不能为空") String identityType, @NotBlank(message = "用户凭证不能为空") String identifier) {
//		return userRsaService.getRSA(identityType, identifier).getPublicKeyBase64();
//	}
//
//	@Logger("用户登录")
//	@GetMapping("login")
//	public User login(@NotBlank(message = "授权方式不能为空") String grantType, String identifier, String credential, HttpServletResponse response) throws AthenaException {
//		// 0. 判断登录方式
//		UserAuthorization userAuthorization = AuthenticationFactory.get(grantType).authorization(identifier, credential);
//		if (userAuthorization == null) {
//			throw new AthenaRuntimeException("UserNotExist");
//		}
//		// 1. 查询用户信息
//		User user = userService.findUserById(userAuthorization.getUserId());
//		// 2. 判断用户状态是否正常
//		if (user.getStatus() != 0) {
//			throw new AthenaRuntimeException("UserDefinedError", new Object[] {"用户名状态不正常"});
//		}
//		// 3. 查询权限数据
//		// 4. 生成Token
//		try {
//			String token = JwtUtil.signature(ConvertUtil.convertBean(user));
//			response.setHeader(BaseConstant.ACCESS_TOKEN, token);
//		} catch (Exception e) {
//			log.error(e.getMessage(), e);
//		}
//		return user;
//	}
//
//	@Logger("查询所有用户信息")
//	@GetMapping("select")
//	public Object select(@Valid User user) {
//		return userService.selectAll(user);
//	}
//
//	/**
//	 * 根据userId查询用户信息
//	 * @param userId
//	 */
//	@GetMapping("select/{userId}")
//	public User findUserById(@PathVariable("userId") String userId) {
//	    return userService.findUserById(userId);
//	}
//
//	@Logger("新增用户信息")
//	@PostMapping("add")
//	public int add(@Valid User user) {
//		return userService.add(user);
//	}
//
//	@Logger("修改用户信息")
//	@PutMapping("update")
//	public int update(@Valid User user) {
//		return userService.update(user);
//	}
//
//	@Logger("删除用户信息")
//	@DeleteMapping("delete/{userId}")
//	public int delete(@PathVariable("userId") String userId) {
//		return userService.delete(userId);
//	}
//
////	@GetMapping("test")
////	@MessageMapping("/sendTest")
////	@SendTo("/topic")
////	public Object test() {
//////		log.info("接收到了信息" + message.getName());
////		return "XYY 测试";
////	}
//	// 接收客户端发送的订阅
////	@SubscribeMapping("/subscribeTest")
//	@MessageMapping("subscribeTest")
//	public Object subscribeTest() {
//		log.info("XXX用户订阅了我。。。");
//		return "XYY 测试";
//	}
//
//	@GetMapping("/subscribeTest")
//	public void greet(String greeting) {
//		String text = "[" + System.currentTimeMillis() + "]:" + greeting;
//		this.messagingTemplate.convertAndSend("/topicTest/subscribeTest", text);
//	}
//
//	@GetMapping("/subscribeTest1")
//	@SendTo("/topicTest/subscribeTest")
//	public String greet1(String greeting) {
//		String text = "[" + System.currentTimeMillis() + "]:" + greeting;
//		return text;
//	}
//
////	@SendTo("/subscribeTest")
////	@GetMapping("push")
////	public void push() {
////		log.info("主动推送数据。。。");
////		messagingTemplate.convertAndSend("/subscribeTest", "主动推送");
////	}
//
//	@MessageMapping("hello")
////	@SendToUser(value = "/topicTest/subscribeTest", broadcast = false)
//	@SendTo("/topicTest/subscribeTest")
//	public String push(String message) {
//		log.info("主动推送数据。。。" + message);
//		return "主动推送数据" + message;
//	}
}
