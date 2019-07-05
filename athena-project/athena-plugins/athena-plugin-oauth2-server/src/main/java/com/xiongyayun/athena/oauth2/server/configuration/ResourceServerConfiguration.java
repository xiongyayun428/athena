package com.xiongyayun.athena.oauth2.server.configuration;

import com.xiongyayun.athena.oauth2.server.exception.AthenaAccessDeniedHandler;
import com.xiongyayun.athena.oauth2.server.exception.AthenaAuthenticationEntryPoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 资源服务器配置类
 *
 * @author: Yayun.Xiong
 * @date: 2019-06-27
 */
@Slf4j
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
	@Autowired
	private TokenStore tokenStore;

	private BearerTokenExtractor tokenExtractor = new BearerTokenExtractor();
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
				.and()
					.authorizeRequests()
					.antMatchers("/login/**","/oauth/**").permitAll()
					// 监控端点内部放行
					.requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
					.anyRequest().authenticated()
				.and()
					.formLogin().loginPage("/login").permitAll()
				.and()
					.logout().permitAll()
					// /logout退出清除cookie
					.addLogoutHandler(new CookieClearingLogoutHandler("token", "remember-me"))
					.logoutSuccessHandler(new LogoutSuccessHandler())
				.and()
					// 认证鉴权错误处理,为了统一异常处理。每个资源服务器都应该加上。
					.exceptionHandling()
						// 自定义访问拒绝
						.accessDeniedHandler(new AthenaAccessDeniedHandler())
						// 自定义未认证处理
						.authenticationEntryPoint(new AthenaAuthenticationEntryPoint())
				.and()
					.csrf().disable()
					// 禁用httpBasic
					.httpBasic().disable();
	}

	public class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
		public LogoutSuccessHandler() {
			// 重定向到原地址
			this.setUseReferer(true);
		}

		@Override
		public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
			try {
				// 解密请求头
				authentication =  tokenExtractor.extract(request);
				if(authentication!=null && authentication.getPrincipal()!=null){
					String tokenValue = authentication.getPrincipal().toString();
					log.debug("revokeToken tokenValue:{}",tokenValue);
					// 移除token
					tokenStore.removeAccessToken(tokenStore.readAccessToken(tokenValue));
				}
			}catch (Exception e){
				log.error("revokeToken error:{}",e);
			}
			super.onLogoutSuccess(request, response, authentication);
		}
	}
}
