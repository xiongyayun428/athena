package com.xiongyayun.athena.spring.boot.admin.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import de.codecentric.boot.admin.server.config.AdminServerProperties;

/**
 * SecurityServerConfig
 *
 * @author Yayun.Xiong
 * @date 2019/03/03
 */
@EnableWebSecurity
public class SecurityServerConfig extends WebSecurityConfigurerAdapter {
	private final String adminContextPath;

	public SecurityServerConfig(AdminServerProperties adminServerProperties) {
		this.adminContextPath = adminServerProperties.getContextPath();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
		successHandler.setTargetUrlParameter("redirectTo");
		successHandler.setDefaultTargetUrl(adminContextPath + "/");

		http
				.authorizeRequests(authorize -> authorize
						.antMatchers(adminContextPath + "/assets/**", adminContextPath + "/login").permitAll()
						.anyRequest().authenticated()
				)
				.formLogin(formLogin -> formLogin
						.loginPage(adminContextPath + "/login")
						.successHandler(successHandler)
				)
				.logout(logout -> logout
						.logoutUrl(adminContextPath + "/logout")
				)
				.httpBasic()
				.and()
				.csrf(csrf -> csrf
						.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
						.ignoringAntMatchers("/instances", "/actuator/**", adminContextPath + "/logout")
				)
		;
	}

}
