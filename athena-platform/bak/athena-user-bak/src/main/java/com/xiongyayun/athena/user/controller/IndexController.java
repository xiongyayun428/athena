package com.xiongyayun.athena.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * IndexController
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2020/6/18
 */
@Slf4j
@Api(value = "所有首页相关服务", tags = {"首页服务"})
@RestController
public class IndexController {
	@ApiOperation("首页")
    @GetMapping("/")
    public String index() {
        return "index";
    }

	@GetMapping("/int")
	public int index2() {
		return 2;
	}

	@GetMapping("/long")
	public long index3() {
		return 3;
	}
}
