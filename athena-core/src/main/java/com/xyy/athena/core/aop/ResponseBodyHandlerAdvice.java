package com.xyy.athena.core.aop;

import com.xyy.athena.core.response.ResBody;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * ResponseHandler
 *
 * @author Yayun.Xiong
 * @date 2019-05-26
 */
@RestControllerAdvice
public class ResponseBodyHandlerAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return methodParameter.hasMethodAnnotation(ResponseBody.class);
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
            Map<String, Object> resultMap = new HashMap<>(3);
            resultMap.put("rtnCode", "000000");
            resultMap.put("rtnMsg", "SUCCESS");
            if (null != returnValue) {
                resultMap.put("rtnData", returnValue);
            }
            return resultMap;
        }
        return returnValue;
    }
}
