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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * ResponseBodyHandlerAdvice
 *
 * @author: Yayun.Xiong
 * @date 2019-05-26
 */
@RestControllerAdvice
public class ResponseBodyHandlerAdvice implements ResponseBodyAdvice<Object> {
	private static final Logger log = LoggerFactory.getLogger(ResponseBodyHandlerAdvice.class);
    private boolean limitLength = true;
    private int limit = 1024;
    private static String[] ignore = {"/error", "/actuator", "/swagger", "/v2/api-docs", "/v3/api-docs"};

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
        if (body != null) {
            HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//			httpServletRequest.getServletPath()
			String uri = httpServletRequest.getRequestURI();
            if ((body.getClass().getPackage().getName().startsWith("org.springframework.boot.actuate") || Arrays.asList(ignore).stream().anyMatch(v -> uri.indexOf(v) > -1))) {
                return body;
            }
        }
		System.out.println(request.getURI().getPath());
		if (body instanceof ResBody) {
			print(body);
			return body;
		}

		ResBody resBody = new ResBody().withData(body);
		if (returnType.getGenericParameterType().equals(String.class)) {
			((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse().setHeader("Content-Type", "application/json");
			return print(resBody);
		}
		print(resBody);
		return resBody;
    }

    private Object print(Object body) {
		try {
			((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse().setHeader("Content-Type", "application/json");
			String value = objectMapper.writeValueAsString(body);
			String truncatedValue = (limitLength && value.length() > limit ? value.substring(0, limit) + " (truncated)..." : value);
			log.info("<<< [RESPONSE]: {}", truncatedValue);
			return value;
		} catch (JsonProcessingException e) {
			log.error("<<< 返回String类型错误: " + e.getMessage(), e);
			return body;
		}
	}
}
