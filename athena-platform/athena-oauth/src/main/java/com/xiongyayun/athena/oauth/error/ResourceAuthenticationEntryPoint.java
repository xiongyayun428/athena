package com.xiongyayun.athena.oauth.error;

import cn.hutool.http.HttpStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiongyayun.athena.core.response.ResBody;
import lombok.SneakyThrows;
import org.bouncycastle.util.encoders.UTF8;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 用来解决匿名用户访问无权限资源时的异常
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/4/27
 */
@Component
public class ResourceAuthenticationEntryPoint implements AuthenticationEntryPoint {
	/**
	 * 编码
	 */
	String UTF8 = "UTF-8";

	/**
	 * JSON 资源
	 */
	String CONTENT_TYPE = "application/json; charset=utf-8";

	private final ObjectMapper objectMapper;

	public ResourceAuthenticationEntryPoint(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		response.setCharacterEncoding(UTF8);
		response.setContentType(CONTENT_TYPE);
		ResBody resBody = new ResBody();
		resBody.setRtnCode(HttpStatus.HTTP_UNAUTHORIZED + "");
		if (authException != null) {
			resBody.setRtnMsg(authException.getMessage());
		}
		response.setStatus(HttpStatus.HTTP_UNAUTHORIZED);
		PrintWriter printWriter = response.getWriter();
		printWriter.append(objectMapper.writeValueAsString(resBody));
	}

}
