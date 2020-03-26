package com.xiongyayun.athena.boot.oauth2.server.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
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
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

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
	/**
	 * 注入权限验证控制器 来支持 password grant type
	 */
	@Autowired
	private AuthenticationManager authenticationManager;
	/**
	 * 设置保存token的方式，一共有五种，这里采用数据库的方式
	 */
	@Qualifier("tokenStore")
	@Autowired
	private TokenStore tokenStore;
	@Autowired
	private ApprovalStore approvalStore;
	/**
	 * 注入userDetailsService，开启refresh_token需要用到
	 */
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private AuthorizationCodeServices authorizationCodeServices;
	@Autowired
	private WebResponseExceptionTranslator webResponseExceptionTranslator;
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
	public void configure(AuthorizationServerSecurityConfigurer security) {
		/**
		 * 配置oauth2服务跨域
		 */
		CorsConfigurationSource source = request -> {
			CorsConfiguration corsConfiguration = new CorsConfiguration();
			corsConfiguration.addAllowedHeader("*");
			corsConfiguration.addAllowedOrigin(request.getHeader( HttpHeaders.ORIGIN));
			corsConfiguration.addAllowedMethod("*");
			corsConfiguration.setAllowCredentials(true);
			corsConfiguration.setMaxAge(3600L);
			return corsConfiguration;
		};
		security
				// 开启/oauth/token_key请求不再拦截
				.tokenKeyAccess("permitAll()")
				// 开启/oauth/check_token验证端口认证权限访问
				.checkTokenAccess("permitAll()")
				// 开启表单认证
				.allowFormAuthenticationForClients()
//				.addTokenEndpointAuthenticationFilter(new CorsFilter(source))
				;
	}

	/**
	 * 配置客户端详情服务
	 * @param clients
	 * @throws Exception
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//		System.out.println(clientDetailsService);
//		clients.withClientDetails(clientDetailsService);
////		super.configure(clients);

		clients.jdbc(dataSource);
	}

	/**
	 * 用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)，还有token的存储方式(tokenStore)
	 * @param endpoints
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//		System.out.println(tokenStore);
////		super.configure(endpoints);
//		endpoints
//				.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
//				.authenticationManager(authenticationManager)
//				.approvalStore(approvalStore)
//				.tokenStore(tokenStore)
////				.tokenEnhancer(tokenEnhancer())
//				.reuseRefreshTokens(false)
//				.userDetailsService(userDetailsService)
////				.accessTokenConverter(OpenHelper.buildAccessTokenConverter())
//				.authorizationCodeServices(authorizationCodeServices);
//		endpoints.setClientDetailsService(clientDetailsService);
////		// 自定义确认授权页面
////		endpoints.pathMapping("/oauth/confirm_access", "/oauth/confirm_access1");
////		// 自定义错误页
////		endpoints.pathMapping("/oauth/error", "/oauth/error1");
//		// 自定义异常转换类
//		endpoints.exceptionTranslator(new AthenaWebResponseExceptionTranslator());
////		endpoints
////				.tokenStore(tokenStore())
//////                .accessTokenConverter(jwtAccessTokenConverter())
////				.authenticationManager(authenticationManager)
////				.exceptionTranslator(webResponseExceptionTranslator)
////				.reuseRefreshTokens(false)
////				.userDetailsService(userDetailsService);

		// 开启密码授权类型
		endpoints.authenticationManager(authenticationManager);
		// 配置token存储方式
		endpoints.tokenStore(tokenStore);
		// 自定义登录或者鉴权失败时的返回信息
		endpoints.exceptionTranslator(webResponseExceptionTranslator);
		// 要使用refresh_token的话，需要额外配置userDetailsService
		endpoints.userDetailsService( userDetailsService );
		// 完成code的生成过程
//        endpoints.authorizationCodeServices();
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
