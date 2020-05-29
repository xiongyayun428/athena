package com.xiongyayun.athena.spring.boot.oauth2.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * MessageController
 *
 * @author: Yayun.Xiong
 * @date: 2020/5/15
 */
@RestController
public class MessageController {
	@GetMapping("/messages")
	public String[] getMessages() {
		String[] messages = new String[] {"Message 1", "Message 2", "Message 3"};
		return messages;
	}
}
