package com.xyy.athena.core.aop;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xyy.athena.core.response.ResBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * ResponseHandler
 *
 * @author Yayun.Xiong
 * @date 2019-05-26
 */
@Slf4j
@RestControllerAdvice()
public class ResponseBodyHandlerAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object returnValue, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (returnValue != null) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            if ((returnValue.getClass().getPackage().getName().startsWith("org.springframework.boot.actuate") || request.getRequestURI().indexOf("actuator") > -1)) {
                return returnValue;
            }
        }

        if (!(returnValue instanceof ResBody)) {
            ResBody body = new ResBody();
            body.setRtnData(returnValue);
            returnValue = body;
        }
        if (log.isDebugEnabled()) {
            ObjectMapper mapper = new ObjectMapper();
            String value = returnValue.toString();
            try {
                value = mapper.writeValueAsString(returnValue);
            } catch (JsonProcessingException e) {
                log.error(e.getMessage(), e);
            }
            log.debug("--->RESPONSE: {}", JSONUtil.toJsonStr(value));
        }
        return returnValue;
    }
}
