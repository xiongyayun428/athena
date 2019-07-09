package com.xiongyayun.athena.oauth2.server.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于tokan校验失败返回信息
 *
 * @author: Yayun.Xiong
 * @date: 2019-07-05
 */
@Component
public class AthenaAuthenticationEntryPoint implements AuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		Map map = new HashMap(2);
		map.put("rtnCode", "401");
		map.put("rtnMsg", authException.getMessage());
//		map.put("path", request.getServletPath());
//		map.put("timestamp", String.valueOf(System.currentTimeMillis()));
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(response.getOutputStream(), map);
		} catch (Exception e) {
			throw new ServletException();
		}
	}
}
