package com.xiongyayun.athena.system.modules;

import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统服务
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/4/15
 */
@Validated
@RestController
@RequestMapping("/sys")
@Api(tags = {"系统服务"})
public class SysController {

	@RequestMapping("/")
	public String home() {
		return "系统服务";
	}
}
