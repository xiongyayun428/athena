package com.xiongyayun.athena.oauth.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import com.xiongyayun.athena.oauth.error.ResourceAccessDeniedHandler;
import com.xiongyayun.athena.oauth.error.ResourceAuthenticationEntryPoint;
import com.xiongyayun.athena.security.handler.FormAuthenticationFailureHandler;
import com.xiongyayun.athena.security.handler.FormLogoutSuccessHandler;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

/**
 * AuthServerConfiguration
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/3/15
 */
@Configuration
@EnableWebSecurity
@Import(OAuth2AuthorizationServerConfiguration.class)
public class AuthServerConfiguration {
	@Resource
	private AuthenticationEntryPoint resourceAuthenticationEntryPoint;

	@Resource
	private AccessDeniedHandler resourceAccessDeniedHandler;

	@Resource
	private PasswordEncoder passwordEncoder;

//	@Resource
//	private RemoteTokenServices remoteTokenServices;

	/**
	 * 定义 spring security 拦击链规则
	 * 配置包括 session策略，无需登录放行的http请求，表单登录，登录参数名称，各种过滤器，拦截器，登录成功/失败的handler，登出后清除session行为，跨域cors的配置
	 * @param http
	 * @return
	 * @throws Exception
	 */
	@Bean
	SecurityFilterChain oauthSecurityFilterChain(HttpSecurity http) throws Exception {
		http
				// 每个请求必须被认证
				.authorizeRequests(
//						authorize -> authorize
////						.mvcMatchers("/login", "/login/form").permitAll()
//						.anyRequest().authenticated()
				).and()
				.httpBasic().and()
				.formLogin(form -> form
						.loginPage("/login")
						.loginProcessingUrl("/login/form")
						.failureHandler(authenticationFailureHandler())
						.permitAll()
				)
				.logout(logout -> logout
						.logoutSuccessHandler(logoutSuccessHandler())
						.deleteCookies("JSESSIONID")
						.invalidateHttpSession(true)
						.clearAuthentication(true)
				)
				.csrf().disable()
//				.exceptionHandling(handling -> handling
//						.authenticationEntryPoint(resourceAuthenticationEntryPoint)
//						.accessDeniedHandler(resourceAccessDeniedHandler)
////						.tokenServices(remoteTokenServices)
//				)
		;
		return http.build();
	}

	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler() {
		return new FormAuthenticationFailureHandler();
	}

	@Bean
	public LogoutSuccessHandler logoutSuccessHandler() {
		return new FormLogoutSuccessHandler();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// 创建登录用户
	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails userDetails = User.builder()
				.username("admin")
				.password(passwordEncoder.encode("admin"))
				.authorities("ROLE_USER")
				.build();
		return new InMemoryUserDetailsManager(userDetails);
	}

	// 创建默认的bean 登录客户端,基于 授权码、 刷新令牌的能力
	@Bean
	public RegisteredClientRepository registeredClientRepository() {
		RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
				.clientId("pig")
				.clientSecret("pig")
				.clientAuthenticationMethod(ClientAuthenticationMethod.BASIC)
				.authorizationGrantTypes(authorizationGrantTypes -> {
					authorizationGrantTypes.add(AuthorizationGrantType.AUTHORIZATION_CODE);
					authorizationGrantTypes.add(AuthorizationGrantType.REFRESH_TOKEN);
				})
				.redirectUri("https://pig4cloud.com")
				.scope("message.read")
				.scope("message.write")
				.build();
		return new InMemoryRegisteredClientRepository(registeredClient);
	}


	// 指定token 生成的加解密密钥
	@Bean
	@SneakyThrows
	public JWKSource<SecurityContext> jwkSource() {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(2048);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

		RSAKey rsaKey= new RSAKey.Builder(publicKey)
				.privateKey(privateKey)
				.keyID(UUID.randomUUID().toString())
				.build();
		JWKSet jwkSet = new JWKSet(rsaKey);
		return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
	}
}
