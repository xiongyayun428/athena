package com.xiongyayun.athena.core.aop;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * 允许在将请求的主体读取和转换成一个对象之前对请求进行自定义，
 * 并允许在将其传递到控制器方法作为一个@RequestBody或HttpEntity方法参数之前处理结果对象。
 *
 * @author: 熊亚运
 * @date: 2019-05-30
 */
@RestControllerAdvice
public class RequestBodyHandlerAdvice  implements RequestBodyAdvice {
	private static final Logger log = LoggerFactory.getLogger(RequestBodyHandlerAdvice.class);
	private ObjectMapper objectMapper;

	@Resource
	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper.copy();
		this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		this.objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false);
	}

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    /**
     * 在数据读出之前可以做的事情
     * @param inputMessage
     * @param parameter
     * @param targetType
     * @param converterType
     * @return
     * @throws IOException
     */
    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
//        Method method = parameter.getMethod();
//        log.info("-----beforeBodyRead -> {}.{}:{}",method.getDeclaringClass().getSimpleName(),method.getName(), targetType.getTypeName());
        return inputMessage;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        Method method = parameter.getMethod();
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		try {
			log.info(">>> [{}]: {}.{}({})", request.getServletPath(), method.getDeclaringClass().getName(), method.getName(), objectMapper.writeValueAsString(body));
		} catch (JsonProcessingException e) {
			log.info(">>> [{}]: {}.{}:{}", request.getServletPath(), method.getDeclaringClass().getName(), method.getName(), body);
			log.error(e.getMessage(), e);
		}
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        Method method = parameter.getMethod();
        log.info(method + " body is empty");
        return body;
    }
}
