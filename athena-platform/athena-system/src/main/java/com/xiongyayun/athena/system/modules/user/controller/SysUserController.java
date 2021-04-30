package com.xiongyayun.athena.system.modules.user.controller;

import com.xiongyayun.athena.core.annotation.Log;
import com.xiongyayun.athena.core.utils.SystemUtil;
import com.xiongyayun.athena.system.modules.user.dto.SysUserDTO;
import com.xiongyayun.athena.system.modules.user.entity.SysUser;
import com.xiongyayun.athena.system.modules.user.service.SysUserService;
import com.xiongyayun.athena.system.modules.user.vo.SysUserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 系统用户
 *
 * @author Yayun.Xiong
 * @date 2019-03-03 16:29
 */
@Validated
@RestController
@RequestMapping("/sys/user")
@Api(value = "sysUser", tags = {"系统用户管理模块"})
public class SysUserController {
	private static final Logger log = LoggerFactory.getLogger(SysUserController.class);
	@Resource
	private SysUserService sysUserService;

//	@Log("数据字典分页查询")
//	@ApiOperation("数据字典分页查询")
//	@PostMapping("/page")
//	public IPage<SysDict> page(@RequestBody(required = false) SysUserVO vo) {
//		SysUser sysUser = new SysUser();
//		BeanUtils.copyProperties(vo, sysUser);
//		Page<SysDict> page = new Page(vo.getPageIndex(), vo.getPageSize());
//		QueryWrapper<SysDict> wrapper = Wrappers.query(sysDict)
//				.and(!ObjectUtils.isEmpty(vo.getCreateTime()) && vo.getCreateTime().length == 2, qw -> qw
//						.ge(ClassUtil.getFieldName(SysDict.class, "createTime"), DateUtil.beginOfDay(vo.getCreateTime()[0]))
//						.le(ClassUtil.getFieldName(SysDict.class, "createTime"), DateUtil.endOfDay(vo.getCreateTime()[1])))
//				.and(!ObjectUtils.isEmpty(vo.getUpdateTime()) && vo.getUpdateTime().length == 2, qw -> qw
//						.ge(ClassUtil.getFieldName(SysDict.class, "updateTime"), DateUtil.beginOfDay(vo.getUpdateTime()[0]))
//						.le(ClassUtil.getFieldName(SysDict.class, "updateTime"), DateUtil.endOfDay(vo.getUpdateTime()[1])));
//		if (vo.getSort() != null) {
//			Arrays.stream(vo.getSort()).forEach(sort -> wrapper.orderBy(!ObjectUtils.isEmpty(sort), "ASC".equals(sort.getValue()), ClassUtil.getFieldName(SysDict.class, sort.getKey())));
//		}
//		return dictMapper.selectPage(page, wrapper);
//	}

	@Log("创建用户")
	@PostMapping("create")
	public boolean create(@Validated @RequestBody SysUserVO vo, HttpServletRequest request) {
		SysUserDTO dto = new SysUserDTO();
		BeanUtils.copyProperties(vo, dto);
		dto.setLastVisitIp(SystemUtil.getClientIP(request));
		return this.sysUserService.create(dto);
	}

	@Log("删除用户")
	@DeleteMapping("/delete/{userid}")
	public boolean delete(@ApiParam("主键ID") @PathVariable("userid") String userid) {
		return this.sysUserService.delete(userid);
	}

	@Log("获取用户详情")
	@GetMapping("get")
	public SysUser get(@ApiParam("主键ID") @RequestParam("userid") String userId) {
		return this.sysUserService.getById(userId);
	}

	@Log("更新用户详情")
	@PostMapping("update")
	public boolean update(@Validated @RequestBody SysUserVO vo, HttpServletRequest request) {
		SysUserDTO dto = new SysUserDTO();
		BeanUtils.copyProperties(vo, dto);
		dto.setLastVisitIp(SystemUtil.getClientIP(request));
		return this.sysUserService.update(dto);
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
