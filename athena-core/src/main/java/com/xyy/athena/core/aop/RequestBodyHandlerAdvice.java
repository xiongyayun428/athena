package com.xyy.athena.core.aop;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * RequestBodyHandlerAdvice
 *
 * @author: 熊亚运
 * @date: 2019-05-30
 */
@Slf4j
@RestControllerAdvice
public class RequestBodyHandlerAdvice  implements RequestBodyAdvice {
    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        Method method = parameter.getMethod();
        log.info("beforeBodyRead -> {}.{}:{}",method.getDeclaringClass().getSimpleName(),method.getName(), targetType.getTypeName());
        return inputMessage;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        Method method = parameter.getMethod();
        log.info("afterBodyRead -> {}.{}:{}",method.getDeclaringClass().getSimpleName(),method.getName(), JSONUtil.toJsonStr(body));
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        Method method = parameter.getMethod();
        log.info("handleEmptyBody -> {}.{}:{}",method.getDeclaringClass().getSimpleName(),method.getName(), JSONUtil.toJsonStr(body));
        return body;
    }
}
