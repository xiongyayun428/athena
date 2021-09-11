package com.xiongyayun.athena.oauth.core;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * AthenaUserDetailsService
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/3/17
 */
public class AthenaUserDetailsService implements UserDetailsService {
	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		UserDetails userDetails = User.builder()
				.username("admin")
				.password("{noop}123456")
				.authorities("ROLE_USER")
				.build();
		return userDetails;
	}
}
