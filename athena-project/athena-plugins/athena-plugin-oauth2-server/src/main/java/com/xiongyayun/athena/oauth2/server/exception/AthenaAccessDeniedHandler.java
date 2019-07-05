package com.xiongyayun.athena.oauth2.server.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * AthenaAccessDeniedHandler
 *
 * @author: Yayun.Xiong
 * @date: 2019-07-05
 */
@Component("athenaAccessDeniedHandler")
public class AthenaAccessDeniedHandler implements AccessDeniedHandler {
	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
		response.setContentType("application/json;charset=UTF-8");
		Map map = new HashMap();
		map.put("error", "400");
		map.put("code", "400");
		map.put("xyymessage", accessDeniedException.getMessage());
		map.put("path", request.getServletPath());
		map.put("timestamp", String.valueOf(System.currentTimeMillis()));
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().write(objectMapper.writeValueAsString(map));
	}
}
