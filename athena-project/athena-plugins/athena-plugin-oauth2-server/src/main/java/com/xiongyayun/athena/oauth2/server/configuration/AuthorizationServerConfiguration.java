package com.xiongyayun.athena.oauth2.server.configuration;

import com.xiongyayun.athena.oauth2.server.exception.AthenaWebResponseExceptionTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

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
	@Autowired
	private DataSource dataSource;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Qualifier("tokenStore")
	@Autowired
	private TokenStore tokenStore;
	@Autowired
	private ApprovalStore approvalStore;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private AuthorizationCodeServices authorizationCodeServices;
	/**
	 * 自定义获取客户端,为了支持自定义权限,
	 */
	@Autowired
	private ClientDetailsService clientDetailsService;

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

		security
				// 开启/oauth/token_key请求不再拦截
				.tokenKeyAccess("permitAll()")
				// 开启/oauth/check_token验证端口认证权限访问
//				.checkTokenAccess("isAuthenticated()")
				.checkTokenAccess("permitAll()")
				// 开启表单认证
				.allowFormAuthenticationForClients();
//		super.configure(security);
	}

	/**
	 * 配置客户端详情服务
	 * @param clients
	 * @throws Exception
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		System.out.println(clientDetailsService);
		clients.withClientDetails(clientDetailsService);
//		super.configure(clients);
	}

	/**
	 * 用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)，还有token的存储方式(tokenStore)
	 * @param endpoints
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		System.out.println(tokenStore);
//		super.configure(endpoints);
		endpoints
				.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
				.authenticationManager(authenticationManager)
				.approvalStore(approvalStore)
				.tokenStore(tokenStore)
//				.tokenEnhancer(tokenEnhancer())
				.reuseRefreshTokens(false)
				.userDetailsService(userDetailsService)
//				.accessTokenConverter(OpenHelper.buildAccessTokenConverter())
				.authorizationCodeServices(authorizationCodeServices);
		endpoints.setClientDetailsService(clientDetailsService);
//		// 自定义确认授权页面
//		endpoints.pathMapping("/oauth/confirm_access", "/oauth/confirm_access1");
//		// 自定义错误页
//		endpoints.pathMapping("/oauth/error", "/oauth/error1");
		// 自定义异常转换类
		endpoints.exceptionTranslator(new AthenaWebResponseExceptionTranslator());
//		endpoints
//				.tokenStore(tokenStore())
////                .accessTokenConverter(jwtAccessTokenConverter())
//				.authenticationManager(authenticationManager)
//				.exceptionTranslator(webResponseExceptionTranslator)
//				.reuseRefreshTokens(false)
//				.userDetailsService(userDetailsService);
	}

//	/**
//	 * 声明 ClientDetails实现
//	 * @return
//	 */
//	@Bean
//	public ClientDetailsService clientDetailsService() {
//		return new JdbcClientDetailsService(dataSource);
//	}

	/**
	 * 授权store
	 *
	 * @return
	 */
	@Bean
	public ApprovalStore approvalStore() {
		return new JdbcApprovalStore(dataSource);
	}

	/**
	 * 授权码
	 *
	 * @return
	 */
	@Bean
	public AuthorizationCodeServices authorizationCodeServices() {
		return new JdbcAuthorizationCodeServices(dataSource);
	}

}
