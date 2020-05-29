package com.xiongyayun.athena.spring.boot.oauth2.server.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 资源服务器配置类
 *
 * @author: Yayun.Xiong
 * @date: 2019-06-27
 */
@Slf4j
@EnableWebSecurity
public class ResourceServerConfiguration extends WebSecurityConfigurerAdapter {
//	@Qualifier("tokenStore")
//	@Autowired
//	private TokenStore tokenStore;
//	@Autowired
//	private AthenaSecurityExpressionHandler athenaOAuth2WebSecurityExpressionHandler;
//	@Autowired
//	private AthenaAuthenticationEntryPoint athenaAuthenticationEntryPoint;
//
//	@Autowired
//	private AthenaAuthenticationSuccessHandler athenaAuthenticationSuccessHandler;
//	@Autowired
//	private AthenaAuthenticationFailureHandler athenaAuthenticationFailureHandler;
//
//	@Autowired
//	private AthenaAccessDeniedHandler athenaAccessDeniedHandler;


//	private BearerTokenExtractor tokenExtractor = new BearerTokenExtractor();
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			.mvcMatcher("/messages/**")
			.authorizeRequests()
			.mvcMatchers("/messages/**").access("hasAuthority('SCOPE_message.read')")
			.and()
			.oauth2ResourceServer()
			.jwt();
//		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//				.and()
//					.authorizeRequests()
//					.antMatchers("/login/**","/oauth/**").permitAll()
//					// 监控端点内部放行
//					.requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
//					.anyRequest().authenticated()
//				.and()
//					.formLogin().loginPage("/login").permitAll()
//				.and()
//					.logout().permitAll()
//					// /logout退出清除cookie
//					.addLogoutHandler(new CookieClearingLogoutHandler("token", "remember-me"))
//					.logoutSuccessHandler(new LogoutSuccessHandler())
//				.and()
//					// 认证鉴权错误处理,为了统一异常处理。每个资源服务器都应该加上。
//					.exceptionHandling()
//						// 自定义访问拒绝
//						.accessDeniedHandler(athenaAccessDeniedHandler)
//						// 自定义未认证处理
//						.authenticationEntryPoint(athenaAuthenticationEntryPoint)
//				.and()
//					.csrf().disable()
//					// 禁用httpBasic
//					.httpBasic().disable();
//
//		http
//			.formLogin()
//				//登录失败过滤器
//				.failureHandler(athenaAuthenticationFailureHandler)
//				//配置登录成功过滤器
//				.successHandler(athenaAuthenticationSuccessHandler)
//				//配置登录地址(调用/logout会跳转到这里)
//				.loginPage("/login1")
//				//配置未登录跳转URL
//				.loginProcessingUrl("/login2")
//			//配置get不需要验证的URL
//			.and()
//				.authorizeRequests()
//				.antMatchers(HttpMethod.GET, "/login", "/login2", "/login1")
//				.permitAll()
////			//配置post不需要验证的URL
////			.and()
////				.authorizeRequests()
////				.antMatchers(HttpMethod.POST,zuulProperties.getAuth().toPostAdapter())
////				.permitAll()
//			//除上述URL,都需要登录用户
//			.and()
//				.authorizeRequests()
//				.anyRequest()
//				.authenticated()
//			//配置用户无权限登录过滤器
//			.and()
//				.exceptionHandling()
//				// 自定义访问拒绝
//				.accessDeniedHandler(athenaAccessDeniedHandler)
//				// 自定义未认证处理
//				.authenticationEntryPoint(athenaAuthenticationEntryPoint)
////			.and()
////				.csrf().disable()
////				.authorizeRequests()
////				.anyRequest()
////				//配置授权验证服务
////				.access("@defaultZuulAuthorizationService.hasPermission(request,authentication)")
//		;
	}

//	//和鉴权服务有关,springboot2.0新加入部分
//	@Override
//	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//		resources.expressionHandler(athenaOAuth2WebSecurityExpressionHandler);
//	}
//
//	public class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
//		public LogoutSuccessHandler() {
//			// 重定向到原地址
//			this.setUseReferer(true);
//		}
//
//		@Override
//		public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//			try {
//				// 解密请求头
//				authentication =  tokenExtractor.extract(request);
//				if(authentication!=null && authentication.getPrincipal()!=null){
//					String tokenValue = authentication.getPrincipal().toString();
//					log.debug("revokeToken tokenValue:{}",tokenValue);
//					// 移除token
//					tokenStore.removeAccessToken(tokenStore.readAccessToken(tokenValue));
//				}
//			}catch (Exception e){
//				log.error("revokeToken error:{}",e);
//			}
//			super.onLogoutSuccess(request, response, authentication);
//		}
//	}
}
