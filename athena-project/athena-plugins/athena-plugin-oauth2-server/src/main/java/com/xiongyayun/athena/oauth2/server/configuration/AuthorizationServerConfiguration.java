package com.xiongyayun.athena.oauth2.server.configuration;

//import com.xiongyayun.athena.oauth2.server.exception.OAuth2WebResponseExceptionTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;

/**
 * 认证授权服务器配置类
 *
 * @author: Yayun.Xiong
 * @date: 2019-06-27
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
//	@Autowired
//	private AuthenticationManager authenticationManager;
//	@Autowired
//	private DataSource dataSource;
	@Autowired
	private RedisConnectionFactory redisConnectionFactory;
//	@Autowired
//	private UserDetailsService userDetailsService;
	/**
	 * 自定义获取客户端,为了支持自定义权限,
	 */
	@Autowired
	@Qualifier(value = "clientDetailsServiceImpl")
	private ClientDetailsService customClientDetailsService;

	/**
	 * 配置授权服务器端点的安全
	 * @param security
	 * @throws Exception
	 */
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//		security.allowFormAuthenticationForClients()
//				.tokenKeyAccess("isAuthenticated()")
//				.checkTokenAccess("permitAll()")
//				.passwordEncoder(passwordEncoder());
//                .addTokenEndpointAuthenticationFilter(integrationAuthenticationFilter);

//		security
//				// 开启/oauth/check_token验证端口认证权限访问
//				.checkTokenAccess("isAuthenticated()")
//				// 开启表单认证
//				.allowFormAuthenticationForClients();
		super.configure(security);
	}

	/**
	 * 配置客户端详情服务
	 * @param clients
	 * @throws Exception
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//		clients.withClientDetails(customClientDetailsService);
		super.configure(clients);
	}

	/**
	 * 用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)，还有token的存储方式(tokenStore)
	 * @param endpoints
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		super.configure(endpoints);
//		endpoints
//				.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
//				.authenticationManager(authenticationManager)
//				.approvalStore(approvalStore())
//				.tokenStore(tokenStore())
////				.tokenEnhancer(tokenEnhancer())
//				.reuseRefreshTokens(false)
//				.userDetailsService(userDetailsService)
////				.accessTokenConverter(OpenHelper.buildAccessTokenConverter())
//				.authorizationCodeServices(authorizationCodeServices());
//		endpoints.setClientDetailsService(customClientDetailsService);
//		// 自定义确认授权页面
//		endpoints.pathMapping("/oauth/confirm_access", "/oauth/confirm_access");
//		// 自定义错误页
//		endpoints.pathMapping("/oauth/error", "/oauth/error");
//		// 自定义异常转换类
////		endpoints.exceptionTranslator(new OAuth2WebResponseExceptionTranslator());
////		endpoints
////				.tokenStore(tokenStore())
//////                .accessTokenConverter(jwtAccessTokenConverter())
////				.authenticationManager(authenticationManager)
////				.exceptionTranslator(webResponseExceptionTranslator)
////				.reuseRefreshTokens(false)
////				.userDetailsService(userDetailsService);
	}

	/**
	 * 令牌存放
	 *
	 * @return
	 */
	@Bean
	public TokenStore tokenStore() {
		return new RedisTokenStore(redisConnectionFactory);
	}


//	/**
//	 * 授权store
//	 *
//	 * @return
//	 */
//	@Bean
//	public ApprovalStore approvalStore() {
//		return new JdbcApprovalStore(dataSource);
//	}

	/**
	 * 令牌信息拓展
	 *
	 * @return
	 */
//	@Bean
//	public TokenEnhancer tokenEnhancer() {
//		return new OpenTokenEnhancer();
//	}

//	/**
//	 * 授权码
//	 *
//	 * @return
//	 */
//	@Bean
//	public AuthorizationCodeServices authorizationCodeServices() {
//		return new JdbcAuthorizationCodeServices(dataSource);
//	}

}
