package com.xiongyayun.athena.spring.boot.oauth2.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * PasswordEncoder
 *
 * @author: Yayun.Xiong
 * @date: 2019-07-09
 */
@Slf4j
public class PasswordEncoderTest {
	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		log.info("secret:  " + encoder.encode("secret"));
	}
}
