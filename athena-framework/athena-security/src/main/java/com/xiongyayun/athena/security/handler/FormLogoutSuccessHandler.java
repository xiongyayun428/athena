package com.xiongyayun.athena.security.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * FormLogoutSuccessHandler
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/4/25
 */
public class FormLogoutSuccessHandler implements LogoutSuccessHandler {
	private static final String REDIRECT_URL = "redirect_url";

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		// 获取请求参数中是否包含 回调地址
		String redirectUrl = request.getParameter(REDIRECT_URL);
		if (StringUtils.hasLength(redirectUrl)) {
			response.sendRedirect(redirectUrl);
		} else {
			// 默认跳转referer 地址
			String referer = request.getHeader(HttpHeaders.REFERER);
			if (StringUtils.hasLength(referer)) {
				response.sendRedirect(referer);
			} else {
				response.getWriter().append("Sign Out Successful!");
			}
		}
	}
}

