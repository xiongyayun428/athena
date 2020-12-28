package com.xiongyayun.athena.core.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiongyayun.athena.core.response.ResBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * ResponseBodyHandlerAdvice
 *
 * @author Yayun.Xiong
 * @date 2019-05-26
 */
@RestControllerAdvice
public class ResponseBodyHandlerAdvice implements ResponseBodyAdvice<Object> {
	private static final Logger log = LoggerFactory.getLogger(ResponseBodyHandlerAdvice.class);
    private static final boolean LIMIT_LENGTH = true;
    private static final int LIMIT = 1024;
    private static final String[] IGNORE = {"/error", "/actuator", "/swagger", "/v2/api-docs", "/v3/api-docs"};

	@Resource
	private ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType selectedContentType,
								  Class<? extends HttpMessageConverter<?>> selectedConverterType,
								  ServerHttpRequest request, ServerHttpResponse response) {
		String uri = request.getURI().getPath();
		if (body != null) {
            if ((body.getClass().getPackage().getName().startsWith("org.springframework.boot.actuate") || Arrays.stream(IGNORE).anyMatch(uri::contains))) {
                return body;
            }
        }
		if (body instanceof ResBody) {
			print(body, response);
			return body;
		}

		ResBody resBody = new ResBody().withData(body);
//		if (returnType.getGenericParameterType().equals(String.class)) {
//			response.getHeaders().setContentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_VALUE));
//			return print(resBody, response);
//		}
		print(resBody, response);
		return resBody;
    }

    private void print(Object body, ServerHttpResponse response) {
		try {
			response.getHeaders().setContentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_VALUE));
			String value = objectMapper.writeValueAsString(body);
			String truncatedValue = (LIMIT_LENGTH && value.length() > LIMIT ? value.substring(0, LIMIT) + " (truncated)..." : value);
			log.info("<<< [RESPONSE]: {}", truncatedValue);
		} catch (JsonProcessingException e) {
			log.error("<<< 返回String类型错误: " + e.getMessage(), e);
		}
	}
}
