package com.xiongyayun.athena.security.handler;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 表单登录失败处理逻辑
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/4/25
 */
public class FormAuthenticationFailureHandler implements AuthenticationFailureHandler {
	private static final Logger log = LoggerFactory.getLogger(FormAuthenticationFailureHandler.class);

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		log.debug("表单登录失败:{}", exception.getLocalizedMessage());
		String url = HttpUtil.encodeParams(String.format("/login?error=%s", exception.getMessage()), CharsetUtil.CHARSET_UTF_8);
		((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse().sendRedirect(url);
	}

}
