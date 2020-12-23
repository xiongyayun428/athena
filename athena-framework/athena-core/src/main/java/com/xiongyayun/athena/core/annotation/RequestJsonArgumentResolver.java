package com.xiongyayun.athena.core.annotation;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

/**
 * RequestJsonHandlerMethodArgumentResolver
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2020/11/27
 */
public class RequestJsonArgumentResolver implements HandlerMethodArgumentResolver {
	private static final String KEY = "TEST_JSON_BODY_KEY";
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	@Override
	public boolean supportsParameter(MethodParameter methodParameter) {
		return methodParameter.hasParameterAnnotation(RequestJson.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		RequestJson requestJson = parameter.getParameterAnnotation(RequestJson.class);
		parameter = parameter.nestedIfOptional();
		HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
		Assert.state(servletRequest != null, "No HttpServletRequest");
//		ServletServerHttpRequest inputMessage = new ServletServerHttpRequest(servletRequest);
//		// 当前请求方式
//		HttpMethod httpMethod = (inputMessage instanceof HttpRequest ? ((HttpRequest) inputMessage).getMethod() : null);
		JSONObject jsonObject = getJsonObject(webRequest);
		String value = getParamName(parameter, requestJson);
		Object paramValue = getParamValue(jsonObject,value);


		if (paramValue == null) {
			if (requestJson != null && requestJson.required() && !parameter.isOptional()) {
				throw new Exception("参数[" + value + "]不能为空。");
			}
			return null;
		}

		Class<?> classType = parameter.getParameterType();
		if (paramValue.getClass().equals(JSONObject.class)){
			paramValue = OBJECT_MAPPER.readValue(paramValue.toString(), classType);
		}
		return paramValue;
	}

	private String getParamName(MethodParameter parameter, RequestJson requestJson) {
		if (requestJson == null) {
			return null;
		}
		String value = requestJson.value();
		if (StringUtils.hasLength(value)) {
			value = parameter.getParameterName();
		}
		return value;
	}

	private Object getParamValue(JSONObject jsonObject,String value) {
		if (jsonObject == null) {
			return null;
		}
		for (String key: jsonObject.keySet()) {
			if(key.equalsIgnoreCase(value)){
				return jsonObject.get(key);
			}
		}
		return null;
	}

	private JSONObject getJsonObject(NativeWebRequest webRequest) throws Exception {
		String jsonBody = (String) webRequest.getAttribute(KEY, NativeWebRequest.SCOPE_REQUEST);
		if(!StringUtils.hasLength(jsonBody)){
			HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
			if (request == null) {
				return null;
			}
			BufferedReader reader = request.getReader();
			StringBuilder sb = new StringBuilder();
			char[] buf = new char[1024];
			int rd;
			while ((rd = reader.read(buf)) != -1) {
				sb.append(buf, 0, rd);
			}
			jsonBody = sb.toString();
			if(!StringUtils.hasLength(jsonBody)){
				Map<String,String[]> params = request.getParameterMap();
				Map tmp = new HashMap(params.size());
				for (Map.Entry<String,String[]> param:params.entrySet()) {
					if(param.getValue().length == 1){
						tmp.put(param.getKey(),param.getValue()[0]);
					}else{
						tmp.put(param.getKey(),param.getValue());
					}
				}
				jsonBody = JSON.toJSONString(tmp);
			}
			webRequest.setAttribute(KEY, jsonBody, NativeWebRequest.SCOPE_REQUEST);
		}
		return JSONObject.parseObject(jsonBody);
	}
}
