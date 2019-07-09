//package com.xiongyayun.athena.oauth2.server.services.impl;
//
//import com.xiongyayun.athena.oauth2.server.security.OAuthUser;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
///**
// * Security用户信息获取实现类
// *
// * @author: Yayun.Xiong
// * @date: 2019-07-01
// */
//@Service("userDetailsService")
//public class UserDetailsServiceImpl implements UserDetailsService {
////	@Autowired
////	private BaseUserAccountRemoteService baseUserAccountRemoteService;
//	/**
//	 * 认证中心名称
//	 */
//	@Value("${spring.application.name}")
//	private String AUTH_SERVICE_ID;
//
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
////		ResultBody<UserAccount> resp = baseUserAccountRemoteService.localLogin(username);
////		UserAccount account = resp.ginMemoryUserDetailsManageretData();
////		if (account == null) {
////			throw new UsernameNotFoundException("系统用户 " + username + " 不存在!");
////		}
////		boolean accountNonLocked = account.getUserProfile().getStatus().intValue() != BaseConstants.USER_STATE_LOCKED;
////		boolean credentialsNonExpired = true;
////		boolean enable = account.getUserProfile().getStatus().intValue() == BaseConstants.USER_STATE_NORMAL ? true : false;
////		boolean accountNonExpired = true;
////		return new OAuthUser(AUTH_SERVICE_ID, account.getUserId(), account.getAccount(), account.getPassword(), account.getUserProfile().getAuthorities(), accountNonLocked, accountNonExpired, enable, credentialsNonExpired,account.getUserProfile().getNickName(),account.getUserProfile().getAvatar());
//		return new OAuthUser();
//	}
//}
